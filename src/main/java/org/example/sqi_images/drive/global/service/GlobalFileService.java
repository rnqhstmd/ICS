package org.example.sqi_images.drive.global.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.BadRequestException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.domain.repository.DepartmentRepository;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.example.sqi_images.drive.global.domain.repository.GlobalFileRepository;
import org.example.sqi_images.drive.global.dto.response.GlobalFileListDto;
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
public class GlobalFileService {

    private final GlobalFileRepository globalFileRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public void uploadGlobalFile(Employee employee, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new BadRequestException(UPLOADED_FILE_EMPTY_ERROR);
        }

        String fileName = file.getOriginalFilename();
        // 중복 파일 이름 검사
        if (globalFileRepository.existsByFileName(fileName)) {
            throw new BadRequestException(DUPLICATED_FILE_NAME_ERROR);
        }
        String fileExtension = getExtensionByFileName(fileName);

        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();

        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR));

        String formattedFileSize = formatFileSize(fileSize);
        String contentType = file.getContentType();

        GlobalFile newFile = new GlobalFile(fileName, fileData, contentType, fileExtension, fileSize, formattedFileSize, employee, department);
        globalFileRepository.save(newFile);
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
    public PageResultDto<GlobalFileListDto, GlobalFile> getGlobalFilesList(int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        Page<GlobalFile> result = globalFileRepository.findAllWithEmployeeAndDepartment(pageRequestDto.toPageable());
        return new PageResultDto<>(result, GlobalFileListDto::of);
    }
}
