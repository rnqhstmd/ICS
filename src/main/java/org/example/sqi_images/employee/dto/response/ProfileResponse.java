package org.example.sqi_images.employee.dto.response;


public record ProfileResponse(
        Long employeeId,
        String name,
        String photoUrl ) {
    public static ProfileResponse of(Long profileId, String name, String photoUrl) {
        return new ProfileResponse(profileId, name, photoUrl);
    }
}
