package org.example.sqi_images.drive.dto.request;


import org.example.sqi_images.drive.domain.DriveAccessType;

public record AssignRoleRequest(
        Long employeeId,
        DriveAccessType role
) {
}
