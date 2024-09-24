package org.example.sqi_images.drive.common.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FileInfoUploadDto(
        @NotBlank(message = "파일 이름을 작성해주세요.")
        String fileName
) {
}
