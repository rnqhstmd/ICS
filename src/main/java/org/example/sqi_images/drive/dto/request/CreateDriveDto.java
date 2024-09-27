package org.example.sqi_images.drive.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateDriveDto(
        @NotBlank(message = "드라이브 이름을 입력해주세요.")
        String driveName
) {
}
