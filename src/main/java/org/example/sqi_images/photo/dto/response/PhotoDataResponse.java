package org.example.sqi_images.photo.dto.response;

public record PhotoDataResponse(byte[] photoData) {
    public static PhotoDataResponse from(byte[] photoData) {
        return new PhotoDataResponse(photoData);
    }
}
