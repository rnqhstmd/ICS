package org.example.sqi_images.drive.global.domain.repository;

import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalFileRepository extends JpaRepository<GlobalFile, Long> {
    boolean existsByFileName(String originalName);
}
