package org.example.sqi_images.drive.global.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.repository.DepartmentRepository;
import org.example.sqi_images.drive.common.dto.request.FileInfoUploadDto;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.example.sqi_images.drive.common.dto.response.FileListDto;
import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.example.sqi_images.drive.global.domain.repository.GlobalFileRepository;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.example.sqi_images.common.exception.type.ErrorType.DEPARTMENT_NOT_FOUND_ERROR;
import static org.example.sqi_images.drive.common.util.FileUtil.*;

@Service
@RequiredArgsConstructor
public class GlobalFileService {

    private final GlobalFileRepository globalFileRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public void uploadGlobalFile(Employee employee, FileInfoUploadDto fileInfoUploadDto, MultipartFile file) throws IOException {
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR));
        String fileName = generateUniqueFileName(fileInfoUploadDto.fileName());
        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();
        String formattedFileSize = formatFileSize(fileSize);

        try (InputStream input = file.getInputStream()) {
            String contentType = tika.detect(input);
            String fileExtension = getFileExtensionByMimeType(input);
            GlobalFile newFile = new GlobalFile(fileName, fileData, contentType, fileExtension, fileSize, formattedFileSize, employee, department);
            globalFileRepository.save(newFile);
        }
    }

    public FileDownloadDto downloadGlobalFile(Long fileId) {
        GlobalFile file = globalFileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorType.FILE_NOT_FOUND_ERROR));

        return FileDownloadDto.of(
                file.getFileName(),
                file.getFileContentType(),
                file.getFileSize(),
                file.getFileData()
        );
    }

    @Transactional(readOnly = true)
    public PageResultDto<FileListDto, GlobalFile> getGlobalFilesList(PageRequestDto pageRequestDto) {
        Page<GlobalFile> result = globalFileRepository.findAll(pageRequestDto.toPageable());
        return new PageResultDto<>(result, FileListDto::ofGlobalFile);
    }

    private String generateUniqueFileName(String originalName) {
        int count = 0;
        String fileName = originalName;
        while (globalFileRepository.existsByFileName(fileName)) {
            count++;
            fileName = originalName + " (" + count + ")";
        }
        return fileName;
    }
}
