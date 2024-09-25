package org.example.sqi_images.drive.department.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.department.service.DepartmentService;
import org.example.sqi_images.drive.common.dto.response.FileDownloadDto;
import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.example.sqi_images.drive.department.domain.repository.DepartmentFileRepository;
import org.example.sqi_images.drive.department.dto.response.DepartmentFileListDto;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.common.util.FileUtil.*;

@Service
@RequiredArgsConstructor
public class DepartmentFileService {

    private final DepartmentService departmentService;
    private final DepartmentFileRepository departmentFileRepository;

    /**
     * 부서 공유 드라이브 파일 업로드
     */
    @Transactional
    public void uploadDepartmentFile(Employee employee,
                                     Long departmentId,
                                     MultipartFile file) throws IOException {
        validaEmptyFile(file);
        String fileName = file.getOriginalFilename();
        validateDuplicatedFileName(departmentFileRepository.existsByFileName(fileName));
        String fileExtension = getExtensionByFileName(fileName);

        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();
        String formattedFileSize = formatFileSize(fileSize);
        String contentType = file.getContentType();

        Department department = departmentService.findExistingDepartmentByType(departmentId);

        DepartmentFile newFile = new DepartmentFile(
                fileName,
                fileData,
                contentType,
                fileExtension,
                fileSize,
                formattedFileSize,
                employee,
                department
        );
        departmentFileRepository.save(newFile);
    }
    /**
     * 부서 공유 드라이브 특정 파일 다운로드
     */
    public FileDownloadDto downloadDepartmentFile(Long fileId) {
        DepartmentFile file = findExistingFileById(fileId);

        return FileDownloadDto.of(
                file.getFileName(),
                file.getFileContentType(),
                file.getFileSize(),
                file.getFileData()
        );
    }

    /**
     * 부서 공유 드라이브 전체 조회
     */
    @Transactional(readOnly = true)
    public PageResultDto<DepartmentFileListDto, DepartmentFile> getDepartmentFileList(Long departmentId,
                                                                                      int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        Page<DepartmentFile> result = departmentFileRepository.findByDepartmentIdWithEmployee(
                departmentId,
                pageRequestDto.toPageable()
        );
        return new PageResultDto<>(result, DepartmentFileListDto::of);
    }

    private DepartmentFile findExistingFileById(Long fileId) {
        return departmentFileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorType.FILE_NOT_FOUND_ERROR));
    }
}
