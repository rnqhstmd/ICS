package org.example.sqi_images.file.controller;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.authentication.annotation.Authenticated;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.file.dto.request.FileInfoUploadDto;
import org.example.sqi_images.file.dto.response.FileDownloadDto;
import org.example.sqi_images.file.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@Authenticated Employee employee,
                                             @RequestPart("file") MultipartFile file,
                                             @RequestPart("fileName") FileInfoUploadDto fileInfoUploadDto) throws IOException {
        fileService.uploadFile(employee, fileInfoUploadDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("프로필 생성 완료");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        FileDownloadDto fileDownloadDto = fileService.downloadFile(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileDownloadDto.fileName());
        headers.setContentType(MediaType.parseMediaType(fileDownloadDto.contentType()));
        headers.setContentLength(fileDownloadDto.fileSize());

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileDownloadDto.data());
    }
}
