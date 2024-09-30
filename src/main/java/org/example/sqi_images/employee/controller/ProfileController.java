package org.example.sqi_images.employee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.dto.request.CreateProfileDto;
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

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProfile(@AuthEmployee Employee employee,
                                                @RequestPart(value = "image") MultipartFile file,
                                                @RequestPart(value = "data") @Valid CreateProfileDto createProfileDto) throws IOException {
        profileService.createProfile(employee, createProfileDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("프로필 생성 완료");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDetailResponse> getProfileDetail(@PathVariable Long id) {
        ProfileDetailResponse profileDetail = profileQueryService.getProfileDetail(id);
        return ResponseEntity.ok(profileDetail);
    }

    @GetMapping("/departments")
    public ResponseEntity<DepartmentProfileList> getProfilesGroupedByPart(@RequestParam("departmentType") DepartmentType departmentType) {
        DepartmentProfileList profileList = profileQueryService.getProfilesGroupedByPart(departmentType);
        return ResponseEntity.ok(profileList);
    }
}
