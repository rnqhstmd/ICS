package org.example.sqi_images.department.domain.repository;

import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsById(Long departmentId);
    Optional<Department> findByDepartmentType(DepartmentType departmentType);
}
