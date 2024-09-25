package org.example.sqi_images.drive.department.dto.response;

import org.example.sqi_images.common.domain.PartType;
import org.example.sqi_images.drive.department.domain.DepartmentFile;

import static org.example.sqi_images.common.constant.Constants.FORMATTER;

public record DepartmentFileListDto(
        Long fileId,
        PartType part,
        String fileName,
        String uploaderName,
        String uploadedDate,
        String fileExtension,
        String fileSize
) {
    public static DepartmentFileListDto of(DepartmentFile file) {
        return new DepartmentFileListDto(
                file.getId(),
                file.getEmployee().getPart().getPartType(),
                file.getFileName(),
                file.getEmployee().getName(),
                file.getCreatedAt().format(FORMATTER),
                file.getFileExtension(),
                file.getFormattedFileSize()
        );
    }
}
