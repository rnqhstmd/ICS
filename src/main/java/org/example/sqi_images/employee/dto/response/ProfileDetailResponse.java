package org.example.sqi_images.employee.dto.response;

import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.common.domain.FrameworkType;
import org.example.sqi_images.common.domain.LanguageType;
import org.example.sqi_images.common.domain.PartType;

public record ProfileDetailResponse(
        Long employeeId,
        String name,
        String email,
        DepartmentType department,
        PartType partType,
        LanguageType languageType,
        FrameworkType frameworkType,
        String photoUrl
) {
    public static ProfileDetailResponse of(
            Long employeeId,
            String name,
            String email,
            DepartmentType department,
            PartType partType,
            LanguageType languageType,
            FrameworkType frameworkType,
            String photoUrl) {
        return new ProfileDetailResponse(employeeId, name, email, department, partType, languageType, frameworkType, photoUrl);
    }
}
