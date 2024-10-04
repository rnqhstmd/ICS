package org.example.sqi_images.file.dto.response;

import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.file.domain.FileInfo;

import static org.example.sqi_images.common.constant.Constants.DATE_FORMATTER;

public record FileInfoResponseDto(
        Long fileId,
        String fileName,
        String uploaderName,
        String date,
        String fileExtension,
        String formattedSize
) {
    public static FileInfoResponseDto notDeletedFilesFrom(FileInfo file) {
        Employee employee = file.getEmployee();
        return new FileInfoResponseDto(
                file.getId(),
                file.getName(),
                employee.getName(),
                file.getCreatedAt().format(DATE_FORMATTER), // 업로드 날짜
                file.getExtension(),
                file.getFormattedFileSize()
        );
    }

    public static FileInfoResponseDto deletedFilesFrom(FileInfo file) {
        Employee employee = file.getEmployee();
        return new FileInfoResponseDto(
                file.getId(),
                file.getName(),
                employee.getName(),
                file.getModifiedAt().format(DATE_FORMATTER), // 삭제된 날짜
                file.getExtension(),
                file.getFormattedFileSize()
        );
    }
}
