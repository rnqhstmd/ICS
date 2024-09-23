package org.example.sqi_images.drive.department.domain.repository;

import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentFileRepository extends JpaRepository<DepartmentFile, Long> {
    Page<DepartmentFile> findByDepartmentId(Long departmentId, Pageable pageable);
    boolean existsByFileName(String originalName);
}
