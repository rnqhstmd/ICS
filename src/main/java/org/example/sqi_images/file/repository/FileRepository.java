package org.example.sqi_images.file.repository;

import org.example.sqi_images.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    boolean existsByFileName(String fileName);
}
