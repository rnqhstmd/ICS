package org.example.sqi_images.employee.domain.repository;

import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    @Query("SELECT e FROM Employee e JOIN FETCH e.detail")
    List<Employee> findAllWithDetails();
}
