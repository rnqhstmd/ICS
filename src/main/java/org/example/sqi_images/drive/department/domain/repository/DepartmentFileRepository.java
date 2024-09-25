package org.example.sqi_images.drive.department.domain.repository;

import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentFileRepository extends JpaRepository<DepartmentFile, Long> {
    boolean existsByFileName(String originalName);
    @Query("SELECT df FROM DepartmentFile df " +
            "JOIN FETCH df.employee e " +
            "JOIN FETCH e.department " +
            "JOIN FETCH e.part " +
            "WHERE df.department.id = :departmentId ")
    Page<DepartmentFile> findByDepartmentIdWithEmployee(Long departmentId, Pageable pageable);
}
