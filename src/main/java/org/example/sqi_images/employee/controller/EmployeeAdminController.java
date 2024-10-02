package org.example.sqi_images.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.Admin;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.dto.request.ProfileDto;
import org.example.sqi_images.employee.service.ProfileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/employees")
@RequiredArgsConstructor
public class EmployeeAdminController {

    private final ProfileService profileService;

    @Admin
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfile(@PathVariable("id") Long updateEmployeeId,
                                                @AuthEmployee Employee employee,
                                                @RequestPart(value = "image", required = false) MultipartFile file,
                                                @RequestPart(value = "data") @Valid ProfileDto profileDto) {
        profileService.updateProfile(employee, updateEmployeeId, profileDto, file);
        return ResponseEntity.ok("관리자 권한 : 해당 사원의 프로필이 성공적으로 수정되었습니다.");
    }

    @Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId) {
        profileService.deleteEmployee(employeeId);
        return ResponseEntity.ok("관리자 권한 : 사원이 성공적으로 삭제되었습니다.");
    }
}
