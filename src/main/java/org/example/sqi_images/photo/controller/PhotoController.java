package org.example.sqi_images.photo.controller;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.photo.dto.response.PhotoDataResponse;
import org.example.sqi_images.photo.service.PhotoQueryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoQueryService photoQueryService;

    @GetMapping("/{photoId}")
    public ResponseEntity<StreamingResponseBody> getImage(@PathVariable Long photoId) {
        PhotoDataResponse photoDataResponse = photoQueryService.getImage(photoId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(outputStream -> {
                    outputStream.write(photoDataResponse.photoData());
                    outputStream.flush();
                });
    }
}
