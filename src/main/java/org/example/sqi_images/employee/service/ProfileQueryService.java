package org.example.sqi_images.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.common.domain.PartType;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.domain.EmployeeDetail;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.example.sqi_images.employee.dto.response.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileQueryService {

    private final EmployeeQueryService employeeQueryService;
    private final EmployeeRepository employeeRepository;

    /**
     * 프로필 단건 조회
     */
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

    /**
     * 부서별 파트 소속 사원 프로필 조회
     */
    @Cacheable(value = "departmentProfiles", key = "#departmentType")
    public DepartmentProfileList getProfilesGroupedByPart(DepartmentType departmentType) {
        List<Employee> employees = employeeRepository.findAllEmployeeByDepartmentType(departmentType);

        Map<PartType, List<ProfileResponse>> groupedProfiles = employees.stream()
                .collect(Collectors.groupingBy(
                        employee -> employee.getPart().getPartType(),
                        Collectors.mapping(
                                employee -> ProfileResponse.of(employee.getId(), employee.getName(), employee.getDetail().getPhotoUrl()),
                                Collectors.toList()
                        )
                ));

        List<PartProfileList> partProfiles = groupedProfiles.entrySet().stream()
                .map(entry -> new PartProfileList(entry.getKey(), entry.getValue()))
                .toList();

        return new DepartmentProfileList(departmentType, partProfiles);
    }
}
