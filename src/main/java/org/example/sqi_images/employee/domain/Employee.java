package org.example.sqi_images.employee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.*;
import org.example.sqi_images.department.domain.Department;
import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.example.sqi_images.drive.global.domain.GlobalFile;
import org.example.sqi_images.employee.dto.request.CreateProfileDto;
import org.example.sqi_images.photo.domain.Photo;

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

    @Column
    private String photoUrl;

    @Column
    @Enumerated(EnumType.STRING)
    private LanguageType languageType;

    @Column
    @Enumerated(EnumType.STRING)
    private FrameworkType frameworkType;

    @Column
    @Enumerated(EnumType.STRING)
    private PartType partType;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentFile> departmentFiles;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GlobalFile> globalFiles;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void updateProfile(CreateProfileDto request, String photoUrl, Photo photo, Department department) {
        this.photoUrl = photoUrl;
        this.languageType = LanguageType.fromValue(request.language());
        this.frameworkType = FrameworkType.fromValue(request.framework());
        this.partType = PartType.fromValue(request.part());
        this.photo = photo;
        this.department = department;
    }
}
