package org.example.sqi_images.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.dto.request.ProfileDto;
import org.example.sqi_images.employee.dto.response.DepartmentProfileList;
import org.example.sqi_images.employee.dto.response.ProfileDetailResponse;
import org.example.sqi_images.employee.service.ProfileQueryService;
import org.example.sqi_images.employee.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileQueryService profileQueryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProfile(@AuthEmployee Employee employee,
                                                @RequestPart(value = "image") MultipartFile file,
                                                @RequestPart(value = "data") @Valid ProfileDto profileDto) throws IOException {
        profileService.createProfile(employee, profileDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("프로필이 성공적으로 생성되었습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDetailResponse> getProfileDetail(@PathVariable("id") Long employeeId) {
        ProfileDetailResponse profileDetail = profileQueryService.getProfileDetail(employeeId);
        return ResponseEntity.ok(profileDetail);
    }

    @GetMapping("/departments")
    public ResponseEntity<DepartmentProfileList> getProfilesGroupedByPart(@RequestParam("departmentType") DepartmentType departmentType) {
        DepartmentProfileList profileList = profileQueryService.getProfilesGroupedByPart(departmentType);
        return ResponseEntity.ok(profileList);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfile(@PathVariable("id") Long employeeId,
                                                @RequestPart(value = "image", required = false) MultipartFile file,
                                                @RequestPart(value = "data") @Valid ProfileDto profileDto) throws IOException {
        profileService.updateProfile(employeeId, profileDto, file);
        return ResponseEntity.ok("프로필이 성공적으로 수정되었습니다.");
    }
}
