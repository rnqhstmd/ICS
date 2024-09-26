package org.example.sqi_images.employee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.common.domain.FrameworkType;
import org.example.sqi_images.common.domain.LanguageType;
import org.example.sqi_images.photo.domain.Photo;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_details")
public class EmployeeDetail extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageType languageType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FrameworkType frameworkType;

    @Column(nullable = false)
    private String photoUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    public EmployeeDetail(String language, String framework, String photoUrl, Photo photo, Employee employee) {
        this.employee = employee;
        this.languageType = LanguageType.fromValue(language);
        this.frameworkType = FrameworkType.fromValue(framework);
        this.photo = photo;
        this.photoUrl = photoUrl;
    }
}
