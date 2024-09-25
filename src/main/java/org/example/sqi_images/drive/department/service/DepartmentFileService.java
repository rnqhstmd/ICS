package org.example.sqi_images.drive.department.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.BadRequestException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.domain.repository.DepartmentRepository;
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

import static org.example.sqi_images.common.exception.type.ErrorType.*;
import static org.example.sqi_images.drive.common.util.FileUtil.*;

@Service
@RequiredArgsConstructor
public class DepartmentFileService {

    private final DepartmentFileRepository departmentFileRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public void uploadDepartmentFile(Employee employee, Long departmentId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new BadRequestException(UPLOADED_FILE_EMPTY_ERROR);
        }

        String fileName = file.getOriginalFilename();
        // 중복 파일 이름 검사
        if (departmentFileRepository.existsByFileName(fileName)) {
            throw new BadRequestException(DUPLICATED_FILE_NAME_ERROR);
        }
        String fileExtension = getExtensionByFileName(fileName);

        // 파일 데이터 추출
        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();

        String formattedFileSize = formatFileSize(fileSize);
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR));

        String contentType = file.getContentType();

        DepartmentFile newFile = new DepartmentFile(fileName, fileData, contentType, fileExtension, fileSize, formattedFileSize, employee, department);
        departmentFileRepository.save(newFile);
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
}
