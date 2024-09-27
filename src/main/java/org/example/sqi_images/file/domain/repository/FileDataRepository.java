package org.example.sqi_images.file.domain.repository;

import org.example.sqi_images.file.domain.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
}
