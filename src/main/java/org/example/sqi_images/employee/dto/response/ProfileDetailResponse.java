package org.example.sqi_images.employee.dto.response;

import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.common.domain.FrameworkType;
import org.example.sqi_images.common.domain.LanguageType;
import org.example.sqi_images.common.domain.PartType;

public record ProfileDetailResponse(
        String name,
        String email,
        DepartmentType department,
        PartType partType,
        LanguageType languageType,
        FrameworkType frameworkType,
        byte[] photo
) {
    public static ProfileDetailResponse of(String name,
                                           String email,
                                           DepartmentType department,
                                           PartType partType,
                                           LanguageType languageType,
                                           FrameworkType frameworkType,
                                           byte[] photo) {
        return new ProfileDetailResponse(name, email, department, partType, languageType, frameworkType, photo);
    }
}
