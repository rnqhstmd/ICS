package org.example.sqi_images.profile.dto.response;

import jakarta.persistence.Lob;
import lombok.Builder;


@Builder
public record ProfileResponse(
        Long profileId,
        String name,
        @Lob byte[] photo) {
    public static ProfileResponse of(Long profileId, String name, byte[] photo) {
        return new ProfileResponse(profileId, name, photo);
    }
}
