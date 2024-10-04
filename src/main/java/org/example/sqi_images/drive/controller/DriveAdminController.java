package org.example.sqi_images.drive.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.Admin;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.drive.service.DriveService;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Admin Drive", description = "관리자 권한 드라이브 관리 API")
@RestController
@RequestMapping("/api/admin/drives")
@RequiredArgsConstructor
public class DriveAdminController {

    private final DriveService driveService;

    @Admin
    @Operation(summary = "공유 드라이브 삭제", description = "관리자 권한으로 특정 공유 드라이브를 삭제합니다.")
    @DeleteMapping("/{driveId}")
    public ResponseEntity<String> deleteDrive(@PathVariable Long driveId) {
        driveService.deleteDrive(driveId);
        return ResponseEntity.ok("관리자 권한 : 공유 드라이브가 성공적으로 삭제되었습니다.");
    }

    @Admin
    @Operation(summary = "공유 드라이브 파일 영구 삭제", description = "관리자 권한으로 공유 드라이브 파일을 삭제합니다.")
    @DeleteMapping("/{driveId}/files/{fileId}")
    public ResponseEntity<String> deleteDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.deleteDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("관리자 권한 : 파일이 영구적으로 삭제되었습니다.");
    }
}
