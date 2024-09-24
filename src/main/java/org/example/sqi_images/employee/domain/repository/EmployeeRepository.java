package org.example.sqi_images.employee.domain.repository;

import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
