package org.example.sqi_images.employee.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.domain.PartType;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.domain.EmployeeDetail;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.example.sqi_images.employee.dto.request.ProfileDto;
import org.example.sqi_images.employee.domain.repository.EmployeeDetailRepository;
import org.example.sqi_images.part.domain.Part;
import org.example.sqi_images.part.service.PartQueryService;
import org.example.sqi_images.photo.domain.Photo;
import org.example.sqi_images.photo.service.PhotoService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import static org.example.sqi_images.common.exception.type.ErrorType.*;
import static org.example.sqi_images.employee.domain.EmployeeRole.ADMIN;
import static org.example.sqi_images.file.util.FileUtil.validaEmptyFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final PhotoService photoService;
    private final PartQueryService partQueryService;
    private final EmployeeQueryService employeeQueryService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeDetailRepository detailRepository;

    /**
     * 프로필 생성
     */
    @CacheEvict(value = "employeeProfiles", key = "#employee.id")
    public void createProfile(Employee employee,
                              ProfileDto profileDto,
                              MultipartFile file) {
        validaEmptyFile(file);
        // 프로필 이미 생성했던 직원 검증
        if (detailRepository.existsByEmployeeId(employee.getId())) {
            throw new ForbiddenException(PROFILE_ALREADY_EXISTS_ERROR);
        }
        // 소속 부서, 파트 업데이트
        Part part = partQueryService.findExistingPartByType(
                PartType.valueOf(profileDto.part()));
        employee.updatePart(part);

        // 사용 언어, 프레임워크, 사진 저장
        Photo photo = photoService.saveImage(file);
        String photoUrl = photoService.generateImageUrl(photo.getId());
        EmployeeDetail detail = new EmployeeDetail(
                profileDto.language(),
                profileDto.framework(),
                photoUrl,
                photo,
                employee
        );
        EmployeeDetail savedDail = detailRepository.save(detail);
        employee.setEmployeeDetailInfo(savedDail);
        employeeRepository.save(employee);
    }

    /**
     * 프로필 업데이트
     */
    @CacheEvict(value = "employeeProfiles", key = "#employee.id")
    public void updateProfile(Employee employee,
                              Long updateEmployeeId,
                              ProfileDto profileDto,
                              MultipartFile file) {
        // 관리자 검증 & 작성자 검증
        if (employee.getRole() != ADMIN) {
            validateUploader(employee.getId(),employee.getId());
        }

        Employee updateEmployee = employeeQueryService.findExistingEmployee(updateEmployeeId);
        // 생성한 프로필이 없는 직원 검증
        EmployeeDetail detail = updateEmployee.getDetail();
        if (detail == null) {
            throw new NotFoundException(PROFILE_NOT_FOUND_ERROR);
        }

        // 소속 부서, 파트 업데이트
        Part part = partQueryService.findExistingPartByType(
                PartType.valueOf(profileDto.part()));
        updateEmployee.updatePart(part);

        // 업데이트할 사진이 있다면 이전 사진 삭제 후 저장
        if (file != null && !file.isEmpty()) {
            Photo oldPhoto = detail.getPhoto();
            if (oldPhoto != null) {
                photoService.deleteImage(oldPhoto);
            }

            Photo newPhoto = photoService.saveImage(file);
            String newPhotoUrl = photoService.generateImageUrl(newPhoto.getId());
            detail.updatePhotoInfo(newPhoto, newPhotoUrl);
        }

        detail.updateDetailInfo(profileDto.language(), profileDto.framework());
        EmployeeDetail savedDail = detailRepository.save(detail);
        updateEmployee.setEmployeeDetailInfo(savedDail);
        employeeRepository.save(updateEmployee);
    }

    private static void validateUploader(Long employeeId, Long updateEmployeeId) {
        if (!employeeId.equals(updateEmployeeId)) {
            throw new ForbiddenException(NO_ADMIN_ACCESS_ERROR);
        }
    }

    /**
     * 사원 삭제
     */
    @Transactional
    public void deleteEmployee(Long employeeId) {
        Employee employeeToDelete = employeeQueryService.findExistingEmployee(employeeId);
        employeeRepository.delete(employeeToDelete);
    }
}
