package org.example.sqi_images.photo.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.InternalServerException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.photo.domain.Photo;
import org.example.sqi_images.photo.domain.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.sqi_images.common.exception.type.ErrorType.IMAGE_UPLOAD_ERROR;


@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    @Value("${app.baseUrl}")
    private String baseUrl;

    @Transactional
    public Photo saveImage(MultipartFile file) {
        try {
            Photo photo = new Photo(file.getBytes());
            return photoRepository.save(photo);
        } catch (IOException ie) {
            throw new InternalServerException(IMAGE_UPLOAD_ERROR, ie.getMessage());
        }
    }

    @Transactional
    public void deleteImage(Photo photo) {
        if (photo != null) {
            photoRepository.delete(photo);
        }
    }

    public String generateImageUrl(Long photoId) {
        return baseUrl + "api/images/" + photoId;
    }
}
