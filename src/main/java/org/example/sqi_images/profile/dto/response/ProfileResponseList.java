package org.example.sqi_images.profile.dto.response;

import java.util.List;

public record ProfileResponseList(
        List<ProfileResponse> profiles
) {
    public static ProfileResponseList from(List<ProfileResponse> profiles) {
        return new ProfileResponseList(profiles);
    }
}
