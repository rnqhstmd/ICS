package org.example.sqi_images.drive.controller;

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

@RestController
@RequestMapping("/api/admin/drives")
@RequiredArgsConstructor
public class DriveAdminController {

    private final DriveService driveService;

    @Admin
    @DeleteMapping("/{driveId}")
    public ResponseEntity<String> deleteDrive(@PathVariable Long driveId) {
        driveService.deleteDrive(driveId);
        return ResponseEntity.ok("관리자 권한 : 공유 드라이브가 성공적으로 삭제되었습니다.");
    }

    @Admin
    @DeleteMapping("/{driveId}/files/{fileId}")
    public ResponseEntity<String> deleteDriveFile(
            @PathVariable Long driveId,
            @AuthEmployee Employee employee,
            @PathVariable Long fileId) {
        driveService.deleteDriveFile(employee, driveId, fileId);
        return ResponseEntity.ok("관리자 권한 : 파일이 영구적으로 삭제되었습니다.");
    }
}
