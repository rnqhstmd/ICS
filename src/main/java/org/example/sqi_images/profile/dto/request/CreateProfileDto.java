package org.example.sqi_images.profile.dto.request;

public record CreateProfileDto(
        String department,
        String part,
        String languages,
        String frameworks
) {
}
