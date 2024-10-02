package org.example.sqi_images.drive.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AssignRoleRequestList(
        @NotBlank(message = "수정할 직원과 권한을 선택해주세요.")
        List<AssignRoleRequest> employeeRoles
) {
}
