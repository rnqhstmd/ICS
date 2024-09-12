package org.example.sqi_images.profile.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.profile.domain.Profile;
import org.example.sqi_images.profile.dto.request.CreateProfileDto;
import org.example.sqi_images.profile.dto.response.ProfileResponse;
import org.example.sqi_images.profile.dto.response.ProfileResponseList;
import org.example.sqi_images.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.example.sqi_images.common.exception.type.ErrorType.PROFILE_ALREADY_EXISTS_ERROR;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public void createProfile(Employee employee, CreateProfileDto createProfileDto, MultipartFile file) throws IOException {
        // 프로필 이미 생성했던 직원 검증
        if (employee.getProfile() != null) {
            throw new ForbiddenException(PROFILE_ALREADY_EXISTS_ERROR);
        }

        // 이미지 파일에서 바이트 배열 추출
        byte[] photoBytes = file.getBytes();

        Profile profile = new Profile(
                photoBytes,
                createProfileDto.department(),
                createProfileDto.part(),
                createProfileDto.languages(),
                createProfileDto.frameworks(),
                employee
        );
        profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public ProfileResponseList getAllProfiles() {
        List<ProfileResponse> profiles = profileRepository.findAll().stream()
                .map(profile -> ProfileResponse.of(
                        profile.getId(),
                        profile.getEmployee().getName(),
                        profile.getPhoto()))
                .toList();
        return ProfileResponseList.from(profiles);
    }
}
