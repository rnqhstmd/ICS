package org.example.sqi_images.photo.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.photo.dto.response.PhotoDataResponse;
import org.example.sqi_images.photo.domain.Photo;
import org.example.sqi_images.photo.domain.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.common.exception.type.ErrorType.PHOTO_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    @Value("${app.baseUrl}")
    private String baseUrl;

    @Transactional
    public Photo saveImage(MultipartFile file) throws IOException {
        Photo photo = new Photo(file.getBytes());
        return photoRepository.save(photo);
    }

    @Transactional(readOnly = true)
    public PhotoDataResponse getImage(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException(PHOTO_NOT_FOUND_ERROR));

        return PhotoDataResponse.from(photo.getPhotoData());
    }

    public String generateImageUrl(Long photoId) {
        return baseUrl + "api/images/" + photoId;
    }
}
