package org.example.sqi_images.file.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.drive.domain.Drive;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.file.domain.FileData;
import org.example.sqi_images.file.domain.FileInfo;
import org.example.sqi_images.file.domain.repository.FileDataRepository;
import org.example.sqi_images.file.domain.repository.FileInfoRepository;
import org.example.sqi_images.file.dto.response.FileDownloadDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.example.sqi_images.common.exception.type.ErrorType.FILE_NOT_FOUND_ERROR;
import static org.example.sqi_images.file.util.FileUtil.*;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileDataRepository fileDataRepository;
    private final FileInfoRepository fileInfoRepository;

    @Transactional
    public FileInfo saveFile(MultipartFile file, Employee employee, Drive drive) throws IOException {
        String fileName = file.getOriginalFilename();
        validateDuplicatedFileName(fileInfoRepository.existsByFileName(fileName));
        String contentType = file.getContentType();
        long fileSize = file.getSize();
        byte[] fileBytes = file.getBytes();

        FileData fileData = new FileData(fileBytes, contentType, fileSize);
        fileDataRepository.save(fileData);

        FileInfo fileInfo = new FileInfo(
                fileName,
                getExtension(fileName),
                formatFileSize(fileSize),
                fileData,
                employee,
                drive
        );
        return fileInfoRepository.save(fileInfo);
    }

    public FileDownloadDto downloadFile(FileInfo fileInfo) {
        Long fileId = fileInfo.getFileData().getId();
        FileData fileData = fileDataRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(FILE_NOT_FOUND_ERROR));

        return FileDownloadDto.of(
                fileInfo.getFileName(),
                fileData.getFileContentType(),
                fileData.getFileSize(),
                fileData.getFileBytes()
        );
    }

    public FileInfo findFileInfoByDriveId(Long fileId, Long driveId) {
        return fileInfoRepository.findByIdAndDriveId(fileId, driveId)
                .orElseThrow(() -> new NotFoundException(FILE_NOT_FOUND_ERROR));
    }
}