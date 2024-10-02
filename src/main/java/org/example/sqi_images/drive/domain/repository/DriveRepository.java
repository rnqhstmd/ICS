package org.example.sqi_images.drive.domain.repository;

import org.example.sqi_images.drive.domain.Drive;
import org.example.sqi_images.drive.dto.response.DriveInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {
    boolean existsById(Long driveId);

    @Query("SELECT new org.example.sqi_images.drive.dto.response.DriveInfo(d.id, d.driveName, COUNT(de)) " +
            "FROM Drive d LEFT JOIN d.driveEmployees de " +
            "GROUP BY d.id, d.driveName")
    List<DriveInfo> findAllDrivesWithEmployeeCount();
}
