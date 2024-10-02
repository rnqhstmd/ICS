package org.example.sqi_images.drive.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.dto.page.request.PageRequestDto;
import org.example.sqi_images.common.dto.page.response.PageResultDto;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.drive.domain.Drive;
import org.example.sqi_images.drive.domain.DriveEmployee;
import org.example.sqi_images.drive.domain.repository.DriveEmployeeRepository;
import org.example.sqi_images.drive.domain.repository.DriveRepository;
import org.example.sqi_images.file.domain.FileInfo;
import org.example.sqi_images.file.domain.repository.FileInfoRepository;
import org.example.sqi_images.file.dto.response.FileInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.sqi_images.common.exception.type.ErrorType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DriveQueryService {

    private final FileInfoRepository fileInfoRepository;
    private final DriveRepository driveRepository;
    private final DriveEmployeeRepository driveEmployeeRepository;

    /**
     * 공유 드라이브 파일 전체 조회
     */
    public PageResultDto<FileInfoResponseDto, FileInfo> getAllDriveFiles(Long driveId, int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        Pageable pageable = pageRequestDto.toPageable();
        Page<FileInfo> result = fileInfoRepository.findByDriveIdWithFetchJoin(driveId, pageable);

        return new PageResultDto<>(result, FileInfoResponseDto::notDeletedFilesFrom);
    }

    /**
     * 공유 드라이브 휴지통 파일 전체 조회
     */
    public PageResultDto<FileInfoResponseDto, FileInfo> getAllTrashFiles(Long driveId, int page) {
        PageRequestDto pageRequestDto = new PageRequestDto(page);
        Pageable pageable = pageRequestDto.toPageable();
        Page<FileInfo> result = fileInfoRepository.findByDriveIdAndIsDeletedTrue(driveId, pageable);

        return new PageResultDto<>(result, FileInfoResponseDto::deletedFilesFrom);
    }

    public void validateExistingDrive(Long driveId) {
        if (!driveRepository.existsById(driveId)) {
            throw new NotFoundException(DRIVE_NOT_FOUND_ERROR);
        }
    }

    public DriveEmployee findExistingAccess(Long driveId, Long employeeId) {
        return driveEmployeeRepository.findByDriveIdAndEmployee_Id(driveId, employeeId)
                .orElseThrow(() -> new ForbiddenException(NO_DRIVE_ACCESS_ERROR));
    }

    public Drive findExistingDrive(Long driveId) {
        return driveRepository.findById(driveId)
                .orElseThrow(() -> new NotFoundException(DRIVE_NOT_FOUND_ERROR));
    }

    public FileInfo findFileInfoByDriveId(Long fileId, Long driveId) {
        return fileInfoRepository.findByIdAndDriveId(fileId, driveId)
                .orElseThrow(() -> new NotFoundException(FILE_NOT_FOUND_ERROR));
    }
}
