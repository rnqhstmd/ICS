package org.example.sqi_images.drive.global.controller;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.example.sqi_images.drive.global.dto.response.GlobalFileListDto;
import org.example.sqi_images.drive.global.service.GlobalFileService;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.drive.common.util.FileUtil.createFileDownloadHeaders;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives/global")
public class GlobalFileController {

    private final GlobalFileService globalFileService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@AuthEmployee Employee employee,
                                             @RequestPart("file") MultipartFile file) throws IOException {
        globalFileService.uploadGlobalFile(employee, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("전체 공유 드라이브 파일 업로드 완료");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) {
        FileDownloadDto fileDownloadDto = globalFileService.downloadGlobalFile(fileId);
        HttpHeaders headers = createFileDownloadHeaders(fileDownloadDto);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileDownloadDto.contentType()))
                .headers(headers)
                .body(new ByteArrayResource(fileDownloadDto.data()));
    }

    @GetMapping("/list")
    public ResponseEntity<PageResultDto<GlobalFileListDto, GlobalFile>> listFiles(@RequestParam(defaultValue = "1") int page) {
        PageResultDto<GlobalFileListDto, GlobalFile> fileList = globalFileService.getGlobalFilesList(page);
        return ResponseEntity.ok(fileList);
    }
}
