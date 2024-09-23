package org.example.sqi_images.drive.common.dto.response;

import lombok.Builder;
import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.example.sqi_images.drive.global.domain.GlobalFile;

import java.time.format.DateTimeFormatter;

@Builder
public record FileListDto(
        Long fileId,
        String uploadedDate,
        String fileName,
        String uploaderName,
        String contentType,
        String fileSize,
        String department
) {
    public static FileListDto ofDepartmentFile(DepartmentFile file) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        return FileListDto.builder()
                .fileId(file.getId())
                .uploadedDate(file.getCreatedAt().format(formatter))
                .fileName(file.getFileName())
                .uploaderName(file.getEmployee().getName())
                .contentType(file.getFileContentType())
                .fileSize(file.getFormattedFileSize())
                .build();
    }

    public static FileListDto ofGlobalFile(GlobalFile file) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        return FileListDto.builder()
                .fileId(file.getId())
                .uploadedDate(file.getCreatedAt().format(formatter))
                .fileName(file.getFileName())
                .uploaderName(file.getEmployee().getName())
                .contentType(file.getFileContentType())
                .fileSize(file.getFormattedFileSize())
                .department(String.valueOf(file.getEmployee().getDepartment().getDepartmentType()))
                .build();
    }
}
