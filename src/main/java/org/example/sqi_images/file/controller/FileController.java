package org.example.sqi_images.file.controller;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.Authenticated;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.file.domain.File;
import org.example.sqi_images.file.dto.request.FileInfoUploadDto;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.file.dto.response.FileDownloadDto;
import org.example.sqi_images.file.dto.response.FileListDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.file.service.FileService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.file.util.FileUtil.createFileDownloadHeaders;

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
        return ResponseEntity.status(HttpStatus.CREATED).body("파일 업로드 완료");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        FileDownloadDto fileDownloadDto = fileService.downloadFile(fileId);
        HttpHeaders headers = createFileDownloadHeaders(fileDownloadDto);
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileDownloadDto.data());
    }

    @GetMapping("/list")
    public ResponseEntity<PageResultDto<FileListDto, File>> listFiles(@RequestParam(defaultValue = "1") int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        PageResultDto<FileListDto, File> fileList = fileService.listFiles(pageRequestDto);
        return ResponseEntity.ok(fileList);
    }
}
