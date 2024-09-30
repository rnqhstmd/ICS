package org.example.sqi_images.employee.dto.response;

import org.example.sqi_images.common.domain.PartType;

import java.util.List;

public record PartProfileList(
        PartType partType,
        List<ProfileResponse> profiles
) {
    public static PartProfileList of(PartType partType, List<ProfileResponse> profiles) {
        return new PartProfileList(partType, profiles);
    }
}
