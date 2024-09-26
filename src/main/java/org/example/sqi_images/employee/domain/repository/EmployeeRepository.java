package org.example.sqi_images.employee.domain.repository;

import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"detail"})
    List<Employee> findAllWithDetails();

    @EntityGraph(attributePaths = {"detail", "part.department"})
    Optional<Employee> findByIdWithDetails(Long employeeId);
}
