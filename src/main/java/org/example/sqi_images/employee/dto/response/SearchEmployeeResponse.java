package org.example.sqi_images.employee.dto.response;

import org.example.sqi_images.employee.domain.Employee;

public record SearchEmployeeResponse(
        Long id,
        String name,
        String email
) {
    public static SearchEmployeeResponse from(Employee employee) {
        return new SearchEmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail()
        );
    }
}
