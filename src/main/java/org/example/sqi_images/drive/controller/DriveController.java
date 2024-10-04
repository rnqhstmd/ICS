package org.example.sqi_images.drive.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.drive.aop.annotation.CheckDriveAccess;
import org.example.sqi_images.drive.domain.Drive;
import org.example.sqi_images.drive.dto.request.AssignRoleRequestList;
import org.example.sqi_images.drive.dto.request.CreateDriveDto;
import org.example.sqi_images.drive.dto.response.DriveInfo;
import org.example.sqi_images.drive.service.DriveQueryService;
import org.example.sqi_images.drive.service.DriveService;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.file.domain.FileInfo;
import org.example.sqi_images.file.dto.response.FileDownloadDto;
import org.example.sqi_images.file.dto.response.FileInfoResponseDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.drive.domain.DriveAccessType.DRIVE_ADMIN;
import static org.example.sqi_images.drive.domain.DriveAccessType.DRIVE_USER;
import static org.example.sqi_images.file.util.FileUtil.createFileDownloadHeaders;
@Tag(name = "Drive", description = "공유 드라이브 관리 API")
@RestController
@RequestMapping("/api/drives")
@RequiredArgsConstructor
public class DriveController {

    private final DriveService driveService;
    private final DriveQueryService driveQueryService;

    @Operation(summary = "공유 드라이브 생성", description = "공유 드라이브를 생성합니다.")
    @PostMapping
    public ResponseEntity<String> createDrive(
            @AuthEmployee Employee employee,
            @RequestBody @Valid CreateDriveDto createDriveDto) {
        driveService.createDrive(employee, createDriveDto);
        return ResponseEntity.ok("공유 드라이브가 성공적으로 생성되었습니다.");
    }

    @Operation(summary = "공유 드라이브 전체 조회", description = "생성되어있는 공유 드라이브 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<PageResultDto<DriveInfo, Drive>> getAllDrives(@RequestParam(defaultValue = "1") int page) {
        PageResultDto<DriveInfo, Drive> result = driveQueryService.getAllDrives(page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "공유 드라이브 파일 업로드", description = "특정 공유 드라이브에 파일을 업로드합니다.")
    @PostMapping("/{driveId}/files")
    @CheckDriveAccess(accessType = {DRIVE_ADMIN, DRIVE_USER})
    public ResponseEntity<String> uploadFileToDrive(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @RequestParam("file") MultipartFile file) throws IOException {
        driveService.uploadFileToDrive(employee, driveId, file);
        return ResponseEntity.ok("공유 드라이브애 파일이 성공적으로 업로드되었습니다.");
    }

    @Operation(summary = "공유 드라이브 권한 부여", description = "특정 공유 드라이브에 대한 권한을 특정 사원에게 부여합니다.")
    @PostMapping("/{driveId}/assign-roles")
    @CheckDriveAccess
    public ResponseEntity<String> assignRoles(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @RequestBody @Valid AssignRoleRequestList assignRoleRequestList) {
        driveService.assignAndUpdateRoles(employee, driveId, assignRoleRequestList);
        return ResponseEntity.ok("권한이 성공적으로 수정되었습니다.");
    }

    @Operation(summary = "공유 드라이브 파일 다운로드", description = "특정 공유 드라이브에 파일을 다운로드합니다.")
    @GetMapping("/{driveId}/files/{fileId}")
    @CheckDriveAccess(accessType = {DRIVE_ADMIN, DRIVE_USER})
    public ResponseEntity<ByteArrayResource> downloadDriveFile(
            @PathVariable Long driveId,
            @PathVariable Long fileId) {
        FileDownloadDto fileDownloadDto = driveService.downloadDriveFile(driveId, fileId);
        HttpHeaders headers = createFileDownloadHeaders(fileDownloadDto);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileDownloadDto.contentType()))
                .headers(headers)
                .body(new ByteArrayResource(fileDownloadDto.data()));
    }

    @Operation(summary = "공유 드라이브 파일 전체 조회", description = "특정 공유 드라이브에 있는 파일 목록을 조회합니다.")
    @GetMapping("/{driveId}/files")
    @CheckDriveAccess(accessType = {DRIVE_ADMIN, DRIVE_USER})
    public ResponseEntity<PageResultDto<FileInfoResponseDto, FileInfo>> getAllDriveFiles(
            @PathVariable Long driveId,
            @RequestParam(defaultValue = "1") int page) {
        PageResultDto<FileInfoResponseDto, FileInfo> result = driveQueryService.getAllDriveFiles(driveId, page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "공유 드라이브 파일 휴지통 이동", description = "특정 공유 드라이브에 있는 파일을 해당 휴지통으로 이동시킵니다.")
    @PostMapping("/{driveId}/files/{fileId}")
    @CheckDriveAccess(accessType = {DRIVE_USER, DRIVE_ADMIN})
    public ResponseEntity<String> setTrashDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.setTrashDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("파일이 휴지통으로 이동되었습니다.");
    }

    @Operation(summary = "공유 드라이브 휴지통 파일 전체 조회", description = "특정 공유 드라이브의 휴지통 파일 목록을 조회합니다.")
    @GetMapping("/{driveId}/trash-files")
    @CheckDriveAccess(accessType = {DRIVE_ADMIN, DRIVE_USER})
    public ResponseEntity<PageResultDto<FileInfoResponseDto, FileInfo>> getAllDriveTrashFiles(
            @PathVariable Long driveId,
            @RequestParam(defaultValue = "1") int page) {
        PageResultDto<FileInfoResponseDto, FileInfo> result = driveQueryService.getAllTrashFiles(driveId, page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "공유 드라이브 휴지통 파일 복원", description = "특정 공유 드라이브의 휴지통 파일을 해당 드라이브로 복원합니다.")
    @PatchMapping("/{driveId}/trash-files/{fileId}")
    @CheckDriveAccess(accessType = {DRIVE_ADMIN, DRIVE_USER})
    public ResponseEntity<String> restoreTrashDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.restoreTrashDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("파일이 드라이브로 복원되었습니다.");
    }

    @Operation(summary = "공유 드라이브 휴지통 파일 영구 삭제", description = "특정 공유 드라이브 휴지통 파일을 영구 삭제합니다.")
    @DeleteMapping("/{driveId}/trash-files/{fileId}")
    @CheckDriveAccess(accessType = {DRIVE_ADMIN, DRIVE_USER})
    public ResponseEntity<String> deleteDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.deleteDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("파일이 영구적으로 삭제되었습니다.");
    }

    @Operation(summary = "공유 드라이브 삭제", description = "특정 공유 드라이브를 삭제합니다.")
    @DeleteMapping("/{driveId}")
    @CheckDriveAccess
    public ResponseEntity<String> deleteDrive(@PathVariable Long driveId) {
        driveService.deleteDrive(driveId);
        return ResponseEntity.ok("공유 드라이브가 성공적으로 삭제되었습니다.");
    }
}
