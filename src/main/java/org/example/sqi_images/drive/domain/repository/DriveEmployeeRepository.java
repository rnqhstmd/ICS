package org.example.sqi_images.drive.domain.repository;

import org.example.sqi_images.drive.domain.DriveEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DriveEmployeeRepository extends JpaRepository<DriveEmployee, Long> {

    Optional<DriveEmployee> findByDriveIdAndEmployee_Id(Long driveId, Long employeeId);

    @Query("SELECT de FROM DriveEmployee de WHERE de.drive.id = :driveId AND de.employee.id IN :employeeIds")
    List<DriveEmployee> findByDriveIdAndEmployeeIds(@Param("driveId") Long driveId, @Param("employeeIds") Set<Long> employeeIds);
}
