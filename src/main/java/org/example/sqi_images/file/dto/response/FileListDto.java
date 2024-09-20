package org.example.sqi_images.file.dto.response;

import org.example.sqi_images.file.domain.File;

import java.time.format.DateTimeFormatter;

public record FileListDto(
        Long fileId,
        String uploadedDate,
        String fileName,
        String uploaderName,
        String contentType,
        String fileSize
) {
    public static FileListDto of(File file) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        return new FileListDto(
                file.getId(),
                file.getCreatedAt().format(formatter),
                file.getFileName(),
                file.getEmployee().getName(),
                file.getFileExtension(),
                file.getFormattedFileSize()
        );
    }
}
