package org.example.sqi_images.employee.dto.response;

import java.util.List;

public record ProfileResponseList(
        List<ProfileResponse> profiles
) {
    public static ProfileResponseList from(List<ProfileResponse> profiles) {
        return new ProfileResponseList(profiles);
    }
}
