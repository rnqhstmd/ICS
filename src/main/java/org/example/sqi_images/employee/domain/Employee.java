package org.example.sqi_images.employee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.*;
import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.example.sqi_images.part.domain.Part;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "detail_id")
    private EmployeeDetail detail;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentFile> departmentFiles;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GlobalFile> globalFiles;

    public Employee(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void updatePart(Part part) {
        this.part = part;
    }

    public void setEmployeeDetailInfo(EmployeeDetail detail) {
        this.detail = detail;
    }
}
