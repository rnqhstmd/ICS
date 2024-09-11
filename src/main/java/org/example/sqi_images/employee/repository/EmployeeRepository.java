package org.example.sqi_images.employee.repository;

import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
