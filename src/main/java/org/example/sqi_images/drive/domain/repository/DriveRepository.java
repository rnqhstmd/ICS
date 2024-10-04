package org.example.sqi_images.drive.domain.repository;

import org.example.sqi_images.drive.domain.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveRepository extends JpaRepository<Drive, Long> {
    boolean existsById(Long driveId);
}
