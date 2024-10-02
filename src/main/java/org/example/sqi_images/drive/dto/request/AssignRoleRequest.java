package org.example.sqi_images.drive.dto.request;


import jakarta.validation.constraints.NotBlank;
import org.example.sqi_images.drive.domain.DriveAccessType;

public record AssignRoleRequest(
        @NotBlank(message = "권한을 수정할 직원의 ID를 입력해주세요.")
        Long employeeId,
        @NotBlank(message = "수정할 권한을 입력해주세요.")
        DriveAccessType role
) {
}
