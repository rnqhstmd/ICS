package org.example.sqi_images.photo.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.photo.domain.Photo;
import org.example.sqi_images.photo.domain.repository.PhotoRepository;
import org.example.sqi_images.photo.dto.response.PhotoDataResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.sqi_images.common.exception.type.ErrorType.PHOTO_NOT_FOUND_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoQueryService {

    private final PhotoRepository photoRepository;

    public PhotoDataResponse getImage(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException(PHOTO_NOT_FOUND_ERROR));

        return PhotoDataResponse.from(photo.getPhotoData());
    }
}
