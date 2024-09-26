package org.example.sqi_images.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import static org.example.sqi_images.common.exception.type.ErrorType.EMPLOYEE_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee findExistingEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR));
    }
}
