package org.example.sqi_images.department.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.common.domain.DepartmentType;
import org.example.sqi_images.employee.domain.Employee;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
public class Department extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private DepartmentType departmentType;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();
}
