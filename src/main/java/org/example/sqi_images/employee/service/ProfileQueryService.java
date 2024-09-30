package org.example.sqi_images.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.domain.EmployeeDetail;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.example.sqi_images.employee.dto.response.ProfileDetailResponse;
import org.example.sqi_images.employee.dto.response.ProfileResponse;
import org.example.sqi_images.employee.dto.response.ProfileResponseList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileQueryService {

    private final EmployeeQueryService employeeQueryService;
    private final EmployeeRepository employeeRepository;

    /**
     * 프로필 전체 조회
     */
    @Transactional(readOnly = true)
    public ProfileResponseList getAllProfiles() {
        List<ProfileResponse> profiles = employeeRepository.findAllWithDetail().stream()
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
        Employee employee = employeeQueryService.findEmployeeWithDetails(employeeId);
        EmployeeDetail detail = employee.getDetail();

        return ProfileDetailResponse.of(
                employeeId,
                employee.getName(),
                employee.getEmail(),
                employee.getPart().getDepartment().getDepartmentType(),
                employee.getPart().getPartType(),
                detail.getLanguageType(),
                detail.getFrameworkType(),
                detail.getPhotoUrl()
        );
    }
}
