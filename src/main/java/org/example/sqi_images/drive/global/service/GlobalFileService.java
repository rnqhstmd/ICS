package org.example.sqi_images.drive.global.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.service.DepartmentService;
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

import static org.example.sqi_images.common.util.FileUtil.*;
import static org.example.sqi_images.common.util.FileUtil.validateDuplicatedFileName;

@Service
@RequiredArgsConstructor
public class GlobalFileService {

    private final DepartmentService departmentService;
    private final GlobalFileRepository globalFileRepository;

    /**
     * 전체 공유 드라이브 파일 업로드
     */
    @Transactional
    public void uploadGlobalFile(Employee employee, MultipartFile file) throws IOException {
        validaEmptyFile(file);
        String fileName = file.getOriginalFilename();
        validateDuplicatedFileName(globalFileRepository.existsByFileName(fileName));
        String fileExtension = getExtensionByFileName(fileName);

        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();
        String formattedFileSize = formatFileSize(fileSize);
        String contentType = file.getContentType();

        Department department = departmentService.findExistingDepartmentByType(
                employee.getDepartment().getId()
        );

        GlobalFile newFile = new GlobalFile(
                fileName,
                fileData,
                contentType,
                fileExtension,
                fileSize,
                formattedFileSize,
                employee,
                department
        );
        globalFileRepository.save(newFile);
    }

    /**
     * 전체 공유 드라이브 특정 파일 다운로드
     */
    public FileDownloadDto downloadGlobalFile(Long fileId) {
        GlobalFile file = findExistingFile(fileId);

        return FileDownloadDto.of(
                file.getFileName(),
                file.getFileContentType(),
                file.getFileSize(),
                file.getFileData()
        );
    }

    private GlobalFile findExistingFile(Long fileId) {
        return globalFileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorType.FILE_NOT_FOUND_ERROR));
    }

    /**
     * 전체 공유 드라이브 전체 조회
     */
    @Transactional(readOnly = true)
    public PageResultDto<GlobalFileListDto, GlobalFile> getGlobalFilesList(int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        Page<GlobalFile> result = globalFileRepository.findAllWithEmployeeAndDepartment(pageRequestDto.toPageable());
        return new PageResultDto<>(result, GlobalFileListDto::of);
    }
}
