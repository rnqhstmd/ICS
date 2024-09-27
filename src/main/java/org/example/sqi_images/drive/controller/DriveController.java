package org.example.sqi_images.drive.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.drive.aop.annotation.CheckDriveAccess;
import org.example.sqi_images.drive.domain.DriveAccessType;
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
        return ResponseEntity.ok("드라이브 생성 완료");
    }

    @PostMapping("/{driveId}/files")
    @CheckDriveAccess(accessType = {DriveAccessType.USER, DriveAccessType.ADMIN})
    public ResponseEntity<String> uploadFileToDrive(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @RequestParam("file") MultipartFile file) throws IOException {
        driveService.uploadFileToDrive(employee, driveId, file);
        return ResponseEntity.ok("파일 업로드 성공");
    }

    @GetMapping("/{driveId}/files/{fileId}")
    @CheckDriveAccess(accessType = {DriveAccessType.USER, DriveAccessType.ADMIN})
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
    @CheckDriveAccess(accessType = {DriveAccessType.USER, DriveAccessType.ADMIN})
    public ResponseEntity<PageResultDto<FileInfoResponseDto, FileInfo>> getAllDriveFiles(
            @PathVariable Long driveId,
            @RequestParam(defaultValue = "1") int page) {
        PageResultDto<FileInfoResponseDto, FileInfo> result = driveService.getAllDriveFiles(driveId, page);
        return ResponseEntity.ok(result);
    }
}