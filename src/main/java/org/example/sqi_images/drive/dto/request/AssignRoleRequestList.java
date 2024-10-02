package org.example.sqi_images.drive.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AssignRoleRequestList(
        @NotEmpty(message = "수정할 직원과 권한을 선택해주세요.")
        List<AssignRoleRequest> employeeRoles
) {
}
