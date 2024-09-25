package org.example.sqi_images.drive.common.util;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static org.example.sqi_images.common.constant.Constants.*;

@Component
public class FileUtil {

    /**
     * 파일 다운로드에 필요한 HttpHeaders 생성 메서드
     */
    public static HttpHeaders createFileDownloadHeaders(FileDownloadDto fileDownloadDto) {
        HttpHeaders headers = new HttpHeaders();
        String fileName = fileDownloadDto.fileName();

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(fileName, StandardCharsets.UTF_8)
                .build();

        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.parseMediaType(fileDownloadDto.contentType()));
        headers.setContentLength(fileDownloadDto.fileSize());

        return headers;
    }

    /**
     * 파일 크기 포멧팅 메서드
     */
    public static String formatFileSize(long sizeInBytes) {
        if (sizeInBytes < KB) {
            return sizeInBytes + " B";
        } else if (sizeInBytes < MB) {
            return DECIMAL_FORMAT.format(sizeInBytes / (double) KB) + " KB";
        } else {
            return DECIMAL_FORMAT.format(sizeInBytes / (double) MB) + " MB";
        }
    }

    /**
     * 파일 이름에서 확장자 추출
     */
    public static String getExtensionByFileName(String fileName) {
        if (fileName == null) {
            return null; // 파일 이름이 null인 경우
        }

        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // 확장자가 없는 경우
        } else if (lastIndexOfDot == fileName.length() - 1) {
            return ""; // 파일 이름이 점으로 끝나는 경우
        } else {
            return fileName.substring(lastIndexOfDot + 1);
        }
    }
}
