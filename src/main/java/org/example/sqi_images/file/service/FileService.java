package org.example.sqi_images.file.service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.file.domain.File;
import org.example.sqi_images.file.dto.request.FileInfoUploadDto;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.file.dto.response.FileDownloadDto;
import org.example.sqi_images.file.dto.response.FileListDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.file.repository.FileRepository;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.example.sqi_images.file.util.FileUtil.*;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    /**
     * 파일 업로드
     */
    @Transactional
    public void uploadFile(Employee employee, FileInfoUploadDto uploadDTO, MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(uploadDTO.fileName());
        byte[] fileData = file.getBytes();
        long fileSize = file.getSize();
        String formattedFileSize = formatFileSize(fileSize);

        try (InputStream input = file.getInputStream()) {
            String contentType = tika.detect(input);
            String fileExtension = getFileExtensionByMimeType(input);
            File newFile = new File(fileName, fileData, contentType, fileExtension, fileSize, formattedFileSize, employee);
            fileRepository.save(newFile);
        }
    }

    /**
     * 파일 다운로드
     */
    public FileDownloadDto downloadFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(ErrorType.FILE_NOT_FOUND_ERROR));

        return FileDownloadDto.of(
                file.getFileName(),
                file.getFileContentType(),
                file.getFileSize(),
                file.getFileData()
        );
    }

    /**
     * 파일 이름 중복 저장 방지용 메서드
     * @param "originalName"
     * @return "originalName (1)"
     */
    private String generateUniqueFileName(String originalName) {
        int count = 0;
        String fileName = originalName;
        while (fileRepository.existsByFileName(fileName)) {
            count++;
            fileName = originalName + " (" + count + ")";
        }
        return fileName;
    }

    /**
     * 업로드된 파일 전체 조회
     */
    @Transactional(readOnly = true)
    public PageResultDto<FileListDto, File> listFiles(PageRequestDto pageRequestDto) {
        Page<File> result = fileRepository.findAll(pageRequestDto.toPageable());
        return new PageResultDto<>(result, FileListDto::of);
    }
}
