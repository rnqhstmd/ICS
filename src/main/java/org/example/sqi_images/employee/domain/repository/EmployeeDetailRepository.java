package org.example.sqi_images.employee.domain.repository;

import org.example.sqi_images.employee.domain.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Long> {
    boolean existsByEmployeeId(Long employeeId);
}
