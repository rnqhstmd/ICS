package org.example.sqi_images.drive.global.controller;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.drive.common.dto.request.FileInfoUploadDto;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.example.sqi_images.drive.common.dto.response.FileListDto;
import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.example.sqi_images.drive.global.service.GlobalFileService;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.http.HttpStatus;
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
                                             @RequestPart("file") MultipartFile file,
                                             @RequestPart("fileName") FileInfoUploadDto fileInfoUploadDto) throws IOException {
        globalFileService.uploadGlobalFile(employee, fileInfoUploadDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("전체 공유 드라이브 파일 업로드 완료");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        FileDownloadDto fileDownloadDto = globalFileService.downloadGlobalFile(fileId);
        return ResponseEntity.ok()
                .headers(createFileDownloadHeaders(fileDownloadDto))
                .body(fileDownloadDto.data());
    }

    @GetMapping("/list")
    public ResponseEntity<PageResultDto<FileListDto, GlobalFile>> listFiles(@RequestParam(defaultValue = "1") int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        PageResultDto<FileListDto, GlobalFile> fileList = globalFileService.getGlobalFilesList(pageRequestDto);
        return ResponseEntity.ok(fileList);
    }
}
