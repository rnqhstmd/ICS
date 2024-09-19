package org.example.sqi_images.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.repository.DepartmentRepository;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.repository.EmployeeRepository;
import org.example.sqi_images.employee.dto.request.CreateProfileDto;
import org.example.sqi_images.employee.dto.response.ImageDataResponse;
import org.example.sqi_images.employee.dto.response.ProfileDetailResponse;
import org.example.sqi_images.employee.dto.response.ProfileResponse;
import org.example.sqi_images.employee.dto.response.ProfileResponseList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.example.sqi_images.common.exception.type.ErrorType.*;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * 프로필 생성
     */
    @Transactional
    public void createProfile(Employee employee, CreateProfileDto createProfileDto, MultipartFile file) throws IOException {
        // 프로필 이미 생성했던 직원 검증
        if (employee.getDepartment() != null) {
            throw new ForbiddenException(PROFILE_ALREADY_EXISTS_ERROR);
        }
        DepartmentType departmentType = DepartmentType.fromValue(createProfileDto.department());
        Department department = departmentRepository.findByDepartmentType(departmentType)
                .orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR));

        // 이미지 파일에서 바이트 배열 추출
        byte[] photoBytes = file.getBytes();

        employee.updateProfile(createProfileDto, photoBytes, department);
        employeeRepository.save(employee);
    }

    /**
     * 프로필 이미지 조회
     */
    public ImageDataResponse getProfileImage(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR));

        byte[] employeePhoto = employee.getPhoto();
        if (employeePhoto == null) {
            throw new NotFoundException(IMAGE_NOT_FOUND_ERROR);
        }

        return ImageDataResponse.of(employeePhoto, "image/png");
    }

    /**
     * 프로필 전체 조회
     */
    @Transactional(readOnly = true)
    public ProfileResponseList getAllProfiles() {
        List<ProfileResponse> profiles = employeeRepository.findAll().stream()
                .map(profile -> ProfileResponse.of(
                        profile.getId(),
                        profile.getName(),
                        profile.getPhoto()))
                .toList();
        return ProfileResponseList.from(profiles);
    }

    /**
     * 프로필 단건 조회
     */
    @Transactional(readOnly = true)
    public ProfileDetailResponse getProfileDetail(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR));

        return ProfileDetailResponse.of(
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment().getDepartmentType(),
                employee.getPartType(),
                employee.getLanguageType(),
                employee.getFrameworkType(),
                employee.getPhoto()
        );
    }
}
