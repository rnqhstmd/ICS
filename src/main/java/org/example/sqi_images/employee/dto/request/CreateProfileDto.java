package org.example.sqi_images.employee.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateProfileDto(
        @Schema(description = "부서 타입", example = "AI",
                allowableValues = {"AI", "SYSTEM", "ENERGY", "MEDIA", "SECURITY", "LAB", "MANAGEMENT"})
        @NotBlank(message = "소속 부서를 입력해주세요.")
        String department,
        @Schema(description = "언어 타입", example = "JAVA",
                allowableValues = {"JAVA", "JAVASCRIPT", "PYTHON"})
        @NotBlank(message = "사용 프로그래밍 언어를 입력해주세요.")
        String language,
        @Schema(description = "프레임워크 타입", example = "SPRING",
                allowableValues = {"SPRING", "REACT",})
        @NotBlank(message = "사용 프레임워크를 입력해주세요.")
        String framework,
        @Schema(description = "파트 타입", example = "AI_SUPPORT",
                allowableValues = {"AI_HEALTH_CARE", "AI_VIDEO_ANALYSIS", "AI_SUPPORT"})
        @NotBlank(message = "소속 파트를 입력해주세요.")
        String part
) {
}
