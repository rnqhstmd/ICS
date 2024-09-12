package org.example.sqi_images.profile.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.profile.domain.Profile;
import org.example.sqi_images.profile.dto.request.CreateProfileDto;
import org.example.sqi_images.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public void createProfile(Employee employee, CreateProfileDto createProfileDto, MultipartFile file) throws IOException {
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
}
