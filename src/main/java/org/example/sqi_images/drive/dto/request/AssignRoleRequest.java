package org.example.sqi_images.drive.dto.request;

import java.util.List;

public record AssignRoleRequest(
        List<EmployeeRoleDto> employeeRoles
) {
}
