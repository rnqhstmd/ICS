package org.example.sqi_images.photo.domain.repository;

import org.example.sqi_images.photo.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
