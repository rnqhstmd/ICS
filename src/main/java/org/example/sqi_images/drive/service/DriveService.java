package org.example.sqi_images.drive.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.drive.domain.Drive;
import org.example.sqi_images.drive.domain.DriveAccessType;
import org.example.sqi_images.drive.domain.DriveEmployee;
import org.example.sqi_images.drive.domain.repository.DriveEmployeeRepository;
import org.example.sqi_images.drive.domain.repository.DriveRepository;
import org.example.sqi_images.drive.dto.request.CreateDriveDto;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.file.domain.FileInfo;
import org.example.sqi_images.file.domain.repository.FileInfoRepository;
import org.example.sqi_images.file.dto.response.FileDownloadDto;
import org.example.sqi_images.file.dto.response.FileInfoResponseDto;
import org.example.sqi_images.file.service.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.common.exception.type.ErrorType.DRIVE_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class DriveService {

    private final FileService fileService;
    private final FileInfoRepository fileInfoRepository;
    private final DriveRepository driveRepository;
    private final DriveEmployeeRepository driveEmployeeRepository;

    public void createDrive(Employee employee, CreateDriveDto createDriveDto) {
        Drive drive = new Drive(createDriveDto.driveName());
        driveRepository.save(drive);

        DriveEmployee driveEmployee = new DriveEmployee(employee, drive, DriveAccessType.ADMIN);
        driveEmployeeRepository.save(driveEmployee);
    }

    public void uploadFileToDrive(Employee employee, Long driveId, MultipartFile file) throws IOException {
        Drive drive = findExistingDrive(driveId);
        fileService.saveFile(file, employee, drive);
    }

    public FileDownloadDto downloadDriveFile(Long driveId, Long fileId) {
        if (!driveRepository.existsById(driveId)) {
            throw new NotFoundException(DRIVE_NOT_FOUND_ERROR);
        }
        FileInfo fileInfo = fileService.findFileInfoByDriveId(fileId, driveId);

        return fileService.downloadFile(fileInfo);
    }

    public PageResultDto<FileInfoResponseDto, FileInfo> getAllDriveFiles(Long driveId, int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        Pageable pageable = pageRequestDto.toPageable();
        Page<FileInfo> result = fileInfoRepository.findByDriveIdWithFetchJoin(driveId, pageable);

        return new PageResultDto<>(result, FileInfoResponseDto::from);
    }

    private Drive findExistingDrive(Long driveId) {
        return driveRepository.findById(driveId)
                .orElseThrow(() -> new NotFoundException(DRIVE_NOT_FOUND_ERROR));
    }
}
