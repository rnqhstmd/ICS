package org.example.sqi_images.drive.global.dto.response;

import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.drive.global.domain.GlobalFile;

import static org.example.sqi_images.common.constant.Constants.FORMATTER;

public record GlobalFileListDto(
        Long fileId,
        DepartmentType department,
        String fileName,
        String uploaderName,
        String uploadedDate,
        String fileExtension,
        String fileSize
) {
    public static GlobalFileListDto of(GlobalFile file) {
        return new GlobalFileListDto(
                file.getId(),
                file.getDepartment().getDepartmentType(),
                file.getFileName(),
                file.getEmployee().getName(),
                file.getCreatedAt().format(FORMATTER),
                file.getFileExtension(),
                file.getFormattedFileSize()
        );
    }
}
