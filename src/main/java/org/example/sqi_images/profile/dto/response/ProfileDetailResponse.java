package org.example.sqi_images.profile.dto.response;

public record ProfileDetailResponse(
        String name,
        String email,
        String department,
        String part,
        String languages,
        String frameworks,
        byte[] photo
) {
    public static ProfileDetailResponse of(String name,
                                           String email,
                                           String department,
                                           String part,
                                           String languages,
                                           String frameworks,
                                           byte[] photo) {
        return new ProfileDetailResponse(name, email, department, part, languages, frameworks, photo);
    }
}
