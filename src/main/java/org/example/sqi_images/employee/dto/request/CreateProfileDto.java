package org.example.sqi_images.employee.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateProfileDto(
        @Schema(description = "부서 타입", example = "AI",
                allowableValues = {
                        "AI", "SYSTEM",
                        "ENERGY", "MEDIA", "SECURITY",
                        "LAB", "MANAGEMENT"
                })
        String department,
        @Schema(description = "언어 타입", example = "JAVA",
                allowableValues = {
                        "JAVA", "JAVASCRIPT",
                        "PYTHON"
                })
        String language,
        @Schema(description = "프레임워크 타입", example = "SPRING",
                allowableValues = {
                        "SPRING", "REACT",
                })
        String framework,
        @Schema(description = "파트 타입", example = "AI_SUPPORT",
                allowableValues = {
                        "AI_HEALTH_CARE", "AI_VIDEO_ANALYSIS",
                        "AI_SUPPORT"
                })
        String part
) {
}
