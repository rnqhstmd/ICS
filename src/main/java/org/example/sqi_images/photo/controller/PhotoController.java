package org.example.sqi_images.photo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.photo.dto.response.PhotoDataResponse;
import org.example.sqi_images.photo.service.PhotoQueryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Tag(name = "Photo", description = "이미지 불러오기 API")
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoQueryService photoQueryService;

    @Operation(summary = "특정 이미지 조회", description = "특정 이미지를 조회합니다.")
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
