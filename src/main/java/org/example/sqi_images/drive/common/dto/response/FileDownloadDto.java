package org.example.sqi_images.drive.common.dto.response;

public record FileDownloadDto(
        String fileName,
        String contentType,
        long fileSize,
        byte[] data
) {
    public static FileDownloadDto of(String fileName,
                                     String contentType,
                                     long fileSize,
                                     byte[] data) {
        return new FileDownloadDto(fileName, contentType, fileSize, data);
    }
}
