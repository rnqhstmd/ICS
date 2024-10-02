package org.example.sqi_images.drive.dto.response;

public record DriveInfo(
        Long driveId,
        String driveName,
        Long memberCount
) {
}
