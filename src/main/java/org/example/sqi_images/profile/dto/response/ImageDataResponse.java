package org.example.sqi_images.profile.dto.response;

public record ImageDataResponse(byte[] imageData, String contentType) {
    public static ImageDataResponse of(byte[] imageData, String contentType) {
        return new ImageDataResponse(imageData, contentType);
    }
}
