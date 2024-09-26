package org.example.sqi_images.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.common.domain.PartType;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.service.DepartmentService;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.domain.EmployeeDetail;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.example.sqi_images.employee.dto.request.CreateProfileDto;
import org.example.sqi_images.employee.dto.response.ProfileDetailResponse;
import org.example.sqi_images.employee.dto.response.ProfileResponse;
import org.example.sqi_images.employee.dto.response.ProfileResponseList;
import org.example.sqi_images.employee.domain.repository.EmployeeDetailRepository;
import org.example.sqi_images.part.domain.Part;
import org.example.sqi_images.part.service.PartService;
import org.example.sqi_images.photo.domain.Photo;
import org.example.sqi_images.photo.service.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.example.sqi_images.common.exception.type.ErrorType.*;
import static org.example.sqi_images.common.util.FileUtil.validaEmptyFile;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final PhotoService photoService;
    private final PartService partService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeDetailRepository detailRepository;

    /**
     * 프로필 생성
     */
    @Transactional
    public void createProfile(Employee employee,
                              CreateProfileDto createProfileDto,
                              MultipartFile file) throws IOException {
        validaEmptyFile(file);
        // 프로필 이미 생성했던 직원 검증
        if (detailRepository.existsByEmployeeId(employee.getId())) {
            throw new ForbiddenException(PROFILE_ALREADY_EXISTS_ERROR);
        }
        // 소속 부서, 파트 업데이트
        Department department = departmentService.findExistingDepartmentByType(
                DepartmentType.fromValue(createProfileDto.department()));
        Part part = partService.findExistingPartByType(
                PartType.valueOf(createProfileDto.part()));
        employee.updateDepartmentAndPart(department,part);

        // 사용 언어, 프레임워크, 사진 저장
        Photo photo = photoService.saveImage(file);
        String photoUrl = photoService.generateImageUrl(photo.getId());
        EmployeeDetail detail = new EmployeeDetail(
                createProfileDto.language(),
                createProfileDto.framework(),
                photoUrl,
                photo,
                employee
        );
        EmployeeDetail savedDail = detailRepository.save(detail);
        employee.setEmployeeDetailInfo(savedDail);
        employeeRepository.save(employee);
    }

    /**
     * 프로필 전체 조회
     */
    @Transactional(readOnly = true)
    public ProfileResponseList getAllProfiles() {
        List<ProfileResponse> profiles = employeeRepository.findAllWithDetails().stream()
                .map(profile ->
                        ProfileResponse.of(
                                profile.getId(),
                                profile.getName(),
                                profile.getDetail().getPhotoUrl()
                        ))
                .toList();
        return ProfileResponseList.from(profiles);
    }

    /**
     * 프로필 단건 조회
     */
    @Transactional(readOnly = true)
    public ProfileDetailResponse getProfileDetail(Long employeeId) {
        Employee employee = employeeService.findExistingEmployee(employeeId);
        EmployeeDetail detail = employee.getDetail();
        return ProfileDetailResponse.of(
                employeeId,
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment().getDepartmentType(),
                employee.getPart().getPartType(),
                detail.getLanguageType(),
                detail.getFrameworkType(),
                detail.getPhotoUrl()
        );
    }
}
