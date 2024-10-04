package org.example.sqi_images.drive.dto.response;

import org.example.sqi_images.drive.domain.Drive;

public record DriveInfo(
        Long driveId,
        String driveName
) {
    public static DriveInfo from(Drive drive) {
        return new DriveInfo(
                drive.getId(),
                drive.getName()
        );
    }
}
