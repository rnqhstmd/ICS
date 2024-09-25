package org.example.sqi_images.common.util;
import org.example.sqi_images.common.exception.BadRequestException;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.example.sqi_images.common.constant.Constants.*;
import static org.example.sqi_images.common.exception.type.ErrorType.DUPLICATED_FILE_NAME_ERROR;
import static org.example.sqi_images.common.exception.type.ErrorType.UPLOADED_FILE_EMPTY_ERROR;

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
     * 파일이 비어 있는지 확인 메서드
     */
    public static void validaEmptyFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException(UPLOADED_FILE_EMPTY_ERROR);
        }
    }

    /**
     * 파일 중복 검사
     */
    public static void validateDuplicatedFileName(boolean exists) {
        if (exists) {
            throw new BadRequestException(DUPLICATED_FILE_NAME_ERROR);
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
