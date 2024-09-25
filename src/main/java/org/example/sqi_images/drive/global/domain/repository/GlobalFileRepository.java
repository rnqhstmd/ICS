package org.example.sqi_images.drive.global.domain.repository;

import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GlobalFileRepository extends JpaRepository<GlobalFile, Long> {
    boolean existsByFileName(String originalName);
    @Query("SELECT gf FROM GlobalFile gf " +
            "JOIN FETCH gf.employee e " +
            "JOIN FETCH e.department ")
    Page<GlobalFile> findAllWithEmployeeAndDepartment(Pageable pageable);
}
