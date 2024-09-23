package org.example.sqi_images.drive.common.util;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

@Component
public class FileUtil {

    public static final Tika tika = new Tika();
    private static final long KB = 1024;
    private static final long MB = 1024 * KB;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

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
     * 파일 확장자 추출 메서드
     */
     public static String getFileExtensionByMimeType(InputStream input) throws IOException {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        String mimeType = tika.detect(input);
        try {
            MimeType type = allTypes.forName(mimeType);
            return type.getExtension();
        } catch (Exception e) {
            return "";
        }
    }
}
