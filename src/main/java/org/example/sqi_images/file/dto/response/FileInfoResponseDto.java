package org.example.sqi_images.file.dto.response;

import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.file.domain.FileInfo;

import static org.example.sqi_images.common.constant.Constants.DATE_FORMATTER;

public record FileInfoResponseDto(
        Long fileId,
        String fileName,
        String uploaderName,
        String uploadedDate,
        String fileExtension,
        String formattedSize
) {
    public static FileInfoResponseDto from(FileInfo file) {
        Employee employee = file.getEmployee();
        return new FileInfoResponseDto(
                file.getId(),
                file.getFileName(),
                employee.getName(),
                file.getCreatedAt().format(DATE_FORMATTER),
                file.getFileExtension(),
                file.getFormattedFileSize()
        );
    }
}
