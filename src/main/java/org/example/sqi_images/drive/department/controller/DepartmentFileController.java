package org.example.sqi_images.drive.department.controller;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.annotation.AuthEmployee;
import org.example.sqi_images.common.aop.annotation.DepartmentMember;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.drive.common.dto.request.FileInfoUploadDto;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.example.sqi_images.drive.common.dto.response.FileListDto;
import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.example.sqi_images.drive.department.service.DepartmentFileService;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.drive.common.util.FileUtil.createFileDownloadHeaders;

@RestController
@RequestMapping("/api/drives/department/{departmentId}")
@RequiredArgsConstructor
public class DepartmentFileController {

    private final DepartmentFileService departmentFileService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @DepartmentMember
    public ResponseEntity<String> uploadDepartmentFile(@AuthEmployee Employee employee,
                                                       @PathVariable Long departmentId,
                                                       @RequestPart("file") MultipartFile file,
                                                       @RequestPart("fileName") FileInfoUploadDto fileInfoUploadDto)
            throws IOException {
        departmentFileService.uploadDepartmentFile(employee, departmentId, fileInfoUploadDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("부서별 공유 드라이브 파일 업로드 완료");
    }

    @GetMapping("/download/{fileId}")
    @DepartmentMember
    public ResponseEntity<byte[]> downloadDepartmentFile(@PathVariable Long departmentId,
                                                         @PathVariable Long fileId) {
        FileDownloadDto fileDownloadDto = departmentFileService.downloadDepartmentFile(fileId);
        return ResponseEntity.ok()
                .headers(createFileDownloadHeaders(fileDownloadDto))
                .body(fileDownloadDto.data());
    }

    @GetMapping("/list")
    @DepartmentMember
    public ResponseEntity<PageResultDto<FileListDto, DepartmentFile>> listFiles(@PathVariable Long departmentId,
                                                                                @RequestParam(defaultValue = "1") int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        PageResultDto<FileListDto, DepartmentFile> fileList = departmentFileService
                .getDepartmentFileList(departmentId, pageRequestDto);
        return ResponseEntity.ok(fileList);
    }
}
