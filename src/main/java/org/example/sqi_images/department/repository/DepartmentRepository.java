package org.example.sqi_images.department.repository;

import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDepartmentType(DepartmentType departmentType);
}
