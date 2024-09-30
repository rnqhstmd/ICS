package org.example.sqi_images.employee.domain.repository;

import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    @Query("SELECT e FROM Employee e " +
            "JOIN FETCH e.detail")
    List<Employee> findAllWithDetail();

    @Query("SELECT e FROM Employee e " +
            "JOIN FETCH e.detail " +
            "WHERE e.id = :id")
    Optional<Employee> findByIdWithDetail(@Param("id") Long id);

    List<Employee> findByEmailContaining(String email);

    @Query("SELECT CASE WHEN COUNT(e.id) = :size " +
            "THEN true " +
            "ELSE false END " +
            "FROM Employee e " +
            "WHERE e.id IN :ids")
    boolean allExistByIds(Set<Long> ids, long size);

    @Query("SELECT e FROM Employee e " +
            "JOIN FETCH e.part p " +
            "JOIN FETCH e.detail d " +
            "WHERE p.department.departmentType = :departmentType " +
            "ORDER BY e.name ASC")
    List<Employee> findAllEmployeeByDepartmentType(@Param("departmentType") DepartmentType departmentType);
}
