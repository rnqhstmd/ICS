package org.example.sqi_images.drive.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.drive.aop.annotation.CheckDriveAccess;
import org.example.sqi_images.drive.dto.request.AssignRoleRequestList;
import org.example.sqi_images.drive.dto.request.CreateDriveDto;
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

import static org.example.sqi_images.drive.domain.DriveAccessType.ADMIN;
import static org.example.sqi_images.drive.domain.DriveAccessType.USER;
import static org.example.sqi_images.file.util.FileUtil.createFileDownloadHeaders;

@RestController
@RequestMapping("/api/drives")
@RequiredArgsConstructor
public class DriveController {

    private final DriveService driveService;

    @PostMapping
    public ResponseEntity<String> createDrive(
            @AuthEmployee Employee employee,
            @RequestBody @Valid CreateDriveDto createDriveDto) {
        driveService.createDrive(employee, createDriveDto);
        return ResponseEntity.ok("공유 드라이브가 성공적으로 생성되었습니다.");
    }

    @PostMapping("/{driveId}/files")
    @CheckDriveAccess(accessType = {ADMIN, USER})
    public ResponseEntity<String> uploadFileToDrive(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @RequestParam("file") MultipartFile file) throws IOException {
        driveService.uploadFileToDrive(employee, driveId, file);
        return ResponseEntity.ok("공유 드라이브애 파일이 성공적으로 업로드되었습니다.");
    }

    @PostMapping("/{driveId}/assign-roles")
    @CheckDriveAccess(accessType = {ADMIN})
    public ResponseEntity<String> assignRoles(
            @PathVariable Long driveId,
            @RequestBody @Valid AssignRoleRequestList assignRoleRequestList) {
        driveService.assignAndUpdateRoles(driveId, assignRoleRequestList);
        return ResponseEntity.ok("권한이 성공적으로 수정되었습니다.");
    }

    @GetMapping("/{driveId}/files/{fileId}")
    @CheckDriveAccess(accessType = {ADMIN, USER})
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

    @GetMapping("/{driveId}/files")
    @CheckDriveAccess(accessType = {ADMIN, USER})
    public ResponseEntity<PageResultDto<FileInfoResponseDto, FileInfo>> getAllDriveFiles(
            @PathVariable Long driveId,
            @RequestParam(defaultValue = "1") int page) {
        PageResultDto<FileInfoResponseDto, FileInfo> result = driveService.getAllDriveFiles(driveId, page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{driveId}/trash-files")
    @CheckDriveAccess(accessType = {ADMIN, USER})
    public ResponseEntity<PageResultDto<FileInfoResponseDto, FileInfo>> getAllDriveTrashFiles(
            @PathVariable Long driveId,
            @RequestParam(defaultValue = "1") int page) {
        PageResultDto<FileInfoResponseDto, FileInfo> result = driveService.getAllTrashFiles(driveId, page);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{driveId}/files/{fileId}")
    @CheckDriveAccess(accessType = {USER, ADMIN})
    public ResponseEntity<String> setTrashDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.setTrashDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("파일이 휴지통으로 이동되었습니다.");
    }

    @PatchMapping("/{driveId}/trash-files/{fileId}")
    @CheckDriveAccess(accessType = {ADMIN, USER})
    public ResponseEntity<String> restoreTrashDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.restoreTrashDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("파일이 드라이브로 복원되었습니다.");
    }

    @DeleteMapping("/{driveId}/trash-files/{fileId}")
    @CheckDriveAccess(accessType = {ADMIN, USER})
    public ResponseEntity<String> deleteDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.deleteDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("파일이 영구적으로 삭제되었습니다.");
    }

    @DeleteMapping("/{driveId}")
    @CheckDriveAccess(accessType = {ADMIN})
    public ResponseEntity<String> deleteDrive(@PathVariable Long driveId) {
        driveService.deleteDrive(driveId);
        return ResponseEntity.ok("공유 드라이브가 성공적으로 삭제되었습니다.");
    }
}
