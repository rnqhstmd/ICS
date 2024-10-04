package org.example.sqi_images.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.ConflictException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.UnauthorizedException;
import org.example.sqi_images.employee.dto.response.SearchEmployeeResponse;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.example.sqi_images.common.exception.type.ErrorType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeQueryService {

    private final EmployeeRepository employeeRepository;

    /**
     * 이메일로 사원 검색
     */
    public List<SearchEmployeeResponse> searchEmployees(String email) {
        List<Employee> employees = employeeRepository.findByEmailContaining(email);
        return employees.stream()
                .map(SearchEmployeeResponse::from)
                .toList();
    }

    public boolean verifyEmployeeIdsExist(Set<Long> employeeIds) {
        return employeeRepository.allExistByIds(employeeIds, employeeIds.size());
    }

    public Employee findExistingEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR));
    }

    public Employee findEmployeeWithDetails(Long employeeId) {
        return employeeRepository.findByIdWithDetail(employeeId)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR));
    }

    public Employee findExistingUserByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException(INVALID_CREDENTIALS_ERROR));
    }

    public void validateIsDuplicatedName(String name) {
        if (employeeRepository.existsByName(name)) {
            throw new ConflictException(DUPLICATED_NAME);
        }
    }

    public void validateIsDuplicatedEmail(String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new ConflictException(DUPLICATED_EMAIL);
        }
    }
}
