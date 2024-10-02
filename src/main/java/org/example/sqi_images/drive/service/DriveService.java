package org.example.sqi_images.drive.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.drive.domain.Drive;
import org.example.sqi_images.drive.domain.DriveEmployee;
import org.example.sqi_images.drive.domain.repository.DriveEmployeeRepository;
import org.example.sqi_images.drive.domain.repository.DriveRepository;
import org.example.sqi_images.drive.dto.request.AssignRoleRequest;
import org.example.sqi_images.drive.dto.request.AssignRoleRequestList;
import org.example.sqi_images.drive.dto.request.CreateDriveDto;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.service.EmployeeQueryService;
import org.example.sqi_images.file.domain.FileInfo;
import org.example.sqi_images.file.dto.response.FileDownloadDto;
import org.example.sqi_images.file.service.FileService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.example.sqi_images.common.exception.type.ErrorType.*;
import static org.example.sqi_images.drive.domain.DriveAccessType.ADMIN;
import static org.example.sqi_images.drive.domain.DriveAccessType.USER;

@Service
@RequiredArgsConstructor
public class DriveService {

    private final FileService fileService;
    private final EmployeeQueryService employeeQueryService;
    private final DriveQueryService driveQueryService;
    private final DriveRepository driveRepository;
    private final DriveEmployeeRepository driveEmployeeRepository;

    /**
     * 공유 드라이브 생성
     */
    public void createDrive(Employee employee, CreateDriveDto createDriveDto) {
        Drive drive = new Drive(createDriveDto.driveName());
        driveRepository.save(drive);

        DriveEmployee driveEmployee = new DriveEmployee(employee, drive, ADMIN, employee);
        driveEmployeeRepository.save(driveEmployee);
    }

    /**
     * 공유 드라이브 권한 부여
     */
    @Transactional
    public void assignAndUpdateRoles(Employee granter, Long driveId, AssignRoleRequestList request) {
        Drive drive = driveQueryService.findExistingDrive(driveId);

        // 요청된 employeeIds 추출
        Set<Long> employeeIds = request.employeeRoles().stream()
                .map(AssignRoleRequest::employeeId)
                .collect(Collectors.toSet());

        // 해당 driveId와 employeeIds 에 해당하는 모든 권한 조회
        Map<Long, DriveEmployee> employeesMap = driveEmployeeRepository.findByDriveIdAndEmployeeIds(driveId, employeeIds)
                .stream()
                .collect(Collectors.toMap(DriveEmployee::getEmployeeId, Function.identity()));

        // 요청된 모든 직원 ID가 실제로 존재하는지 확인
        if (!employeeQueryService.verifyEmployeeIdsExist(employeeIds)) {
            throw new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR);
        }

        List<DriveEmployee> toUpdate = new ArrayList<>();
        List<DriveEmployee> toDelete = new ArrayList<>();

        request.employeeRoles().forEach(assignRoleRequest -> {
            DriveEmployee existingEmployee = employeesMap.get(assignRoleRequest.employeeId());
            if (existingEmployee != null) {
                if (assignRoleRequest.role() == null) {
                    toDelete.add(existingEmployee);
                } else {
                    existingEmployee.setRole(assignRoleRequest.role());
                    existingEmployee.setGranter(granter);
                    toUpdate.add(existingEmployee);
                }
            } else {
                // 존재하지 않는 직원 ID에 대한 처리는 필요 없음, 이미 검사 완료
                Employee newEmployee = employeeQueryService.findExistingEmployee(assignRoleRequest.employeeId());
                toUpdate.add(new DriveEmployee(newEmployee, drive, assignRoleRequest.role(), granter));
            }
        });

        if (!toDelete.isEmpty()) {
            driveEmployeeRepository.deleteAllInBatch(toDelete);
        }
        if (!toUpdate.isEmpty()) {
            driveEmployeeRepository.saveAll(toUpdate);
        }
    }

    /**
     * 공유 드라이브 파일 업로드
     */
    public void uploadFileToDrive(Employee employee, Long driveId, MultipartFile file) throws IOException {
        Drive drive = driveQueryService.findExistingDrive(driveId);
        fileService.saveFile(file, employee, drive);
    }

    /**
     * 공유 드라이브 파일 다운로드
     */
    public FileDownloadDto downloadDriveFile(Long driveId, Long fileId) {
        driveQueryService.validateExistingDrive(driveId);
        FileInfo fileInfo = driveQueryService.findFileInfoByDriveId(fileId, driveId);

        return fileService.downloadFile(fileInfo);
    }

    /**
     * 공유 드라이브 파일 휴지통 이동
     */
    public void setTrashDriveFile(Employee employee, Long driveId, Long fileId) {
        FileInfo fileInfo = driveQueryService.findFileInfoByDriveId(fileId, driveId);

        validateAccessOrUploader(driveId, employee.getId(), fileInfo);
        fileService.setTrashFile(fileInfo);
    }

    /**
     * 공유 드라이브 휴지통 파일 전 드라이브로 복원
     */
    public void restoreTrashDriveFile(Employee employee, Long driveId, Long fileId) {
        FileInfo fileInfo = driveQueryService.findFileInfoByDriveId(fileId, driveId);

        validateAccessOrUploader(driveId, employee.getId(), fileInfo);
        fileService.restoreFile(fileInfo);
    }

    /**
     * 공유 드라이브 휴지통 파일 영구 삭제
     */
    public void deleteDriveFile(Employee employee, Long driveId, Long fileId) {
        FileInfo fileInfo = driveQueryService.findFileInfoByDriveId(fileId, driveId);

        validateAccessOrUploader(driveId, employee.getId(), fileInfo);
        fileService.deleteFile(fileInfo);
    }

    // 매일 자정에 30일 지난 파일 삭제 스케쥴러
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleOldTrashFilesDeletion() {
        fileService.deleteOldTrashFiles();
    }

    /**
     * 공유 드라이브 삭제
     */
    @Transactional
    public void deleteDrive(Long driveId) {
        Drive drive = driveQueryService.findExistingDrive(driveId);
        driveRepository.delete(drive);
    }

    // ADMIN 권한 보유 or USER 권한자면서 파일 업로더 검증
    private void validateAccessOrUploader(Long driveId, Long employeeId, FileInfo fileInfo) {
        DriveEmployee driveEmployee = driveQueryService.findExistingAccess(driveId, employeeId);
        boolean isAdmin = driveEmployee.getRole() == ADMIN;
        boolean isUploader = fileInfo.getEmployee().getId().equals(employeeId);

        if (!(isAdmin || (driveEmployee.getRole() == USER && isUploader))) {
            throw new ForbiddenException(NO_ADMIN_ACCESS_ERROR);
        }
    }
}
