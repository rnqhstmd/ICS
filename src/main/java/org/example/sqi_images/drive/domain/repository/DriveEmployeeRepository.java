package org.example.sqi_images.drive.domain.repository;

import org.example.sqi_images.drive.domain.DriveEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriveEmployeeRepository extends JpaRepository<DriveEmployee, Long> {

    Optional<DriveEmployee> findByDriveIdAndEmployeeId(Long driveId, Long employeeId);
}
