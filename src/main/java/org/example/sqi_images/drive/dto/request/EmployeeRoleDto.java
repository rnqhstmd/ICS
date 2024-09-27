package org.example.sqi_images.drive.dto.request;

import org.example.sqi_images.drive.domain.DriveAccessType;

public record EmployeeRoleDto(
        Long employeeId,
        DriveAccessType role
) {
}
