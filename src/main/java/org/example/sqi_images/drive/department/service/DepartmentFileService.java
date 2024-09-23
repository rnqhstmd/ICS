package org.example.sqi_images.drive.department.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.domain.repository.DepartmentRepository;
import org.example.sqi_images.drive.common.dto.request.FileInfoUploadDto;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.example.sqi_images.drive.common.dto.response.FileListDto;
import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.example.sqi_images.drive.department.domain.repository.DepartmentFileRepository;
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
public class DepartmentFileService {

    private final DepartmentFileRepository departmentFileRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public void uploadDepartmentFile(Employee employee, Long departmentId, FileInfoUploadDto fileInfoUploadDto, MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(fileInfoUploadDto.fileName());
        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();
        String formattedFileSize = formatFileSize(fileSize);
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR));

        try (InputStream input = file.getInputStream()) {
            String contentType = tika.detect(input);
            String fileExtension = getFileExtensionByMimeType(input);
            DepartmentFile newFile = new DepartmentFile(fileName, fileData, contentType, fileExtension, fileSize, formattedFileSize, employee, department);
            departmentFileRepository.save(newFile);
        }
    }
    
    public FileDownloadDto downloadDepartmentFile(Long fileId) {
        DepartmentFile file = departmentFileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorType.FILE_NOT_FOUND_ERROR));

        return FileDownloadDto.of(
                file.getFileName(),
                file.getFileContentType(),
                file.getFileSize(),
                file.getFileData()
        );
    }

    @Transactional(readOnly = true)
    public PageResultDto<FileListDto, DepartmentFile> getDepartmentFileList(Long departmentId, PageRequestDto pageRequestDto) {
        Page<DepartmentFile> result = departmentFileRepository.findByDepartmentId(departmentId, pageRequestDto.toPageable());
        return new PageResultDto<>(result, FileListDto::ofDepartmentFile);
    }

    private String generateUniqueFileName(String originalName) {
        int count = 0;
        String fileName = originalName;
        while (departmentFileRepository.existsByFileName(fileName)) {
            count++;
            fileName = originalName + " (" + count + ")";
        }
        return fileName;
    }
}
