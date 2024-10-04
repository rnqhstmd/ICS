package org.example.sqi_images.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
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

@Tag(name = "Employee", description = "사원 프로필 관리 API")
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileQueryService profileQueryService;

    @Operation(summary = "사원 프로필 생성", description = "사원의 프로필을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProfile(@AuthEmployee Employee employee,
                                                @RequestPart(value = "image") MultipartFile file,
                                                @RequestPart(value = "data") @Valid ProfileDto profileDto) {
        profileService.createProfile(employee, profileDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("프로필이 성공적으로 생성되었습니다.");
    }

    @Operation(summary = "특정 사원 프로필 조회", description = "특정 사원의 프로필을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDetailResponse> getProfileDetail(@PathVariable("id") Long employeeId) {
        ProfileDetailResponse profileDetail = profileQueryService.getProfileDetail(employeeId);
        return ResponseEntity.ok(profileDetail);
    }

    @Operation(summary = "부서 파트별 소속 프로필 전체 조회", description = "부서 파트별로 나눠진 사원 리스트를 반환합니다.")
    @GetMapping("/departments")
    public ResponseEntity<DepartmentProfileList> getProfilesGroupedByPart(@RequestParam("departmentType") String type) {
        DepartmentProfileList profileList = profileQueryService.getProfilesGroupedByPart(type);
        return ResponseEntity.ok(profileList);
    }

    @Operation(summary = "특정 사원 프로필 수정", description = "특정 사원의 프로필을 수정합니다.")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfile(@PathVariable("id") Long updateEmployeeId,
                                                @AuthEmployee Employee employee,
                                                @RequestPart(value = "image", required = false) MultipartFile file,
                                                @RequestPart(value = "data") @Valid ProfileDto profileDto) {
        profileService.updateProfile(employee, updateEmployeeId, profileDto, file);
        return ResponseEntity.ok("프로필이 성공적으로 수정되었습니다.");
    }
}
