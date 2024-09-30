package org.example.sqi_images.employee.dto.response;

import org.example.sqi_images.common.domain.DepartmentType;

import java.util.List;

public record DepartmentProfileList(
        DepartmentType departmentType,
        List<PartProfileList> partProfiles
) {
    public static DepartmentProfileList of(DepartmentType departmentType, List<PartProfileList> partProfiles) {
        return new DepartmentProfileList(departmentType, partProfiles);
    }
}
