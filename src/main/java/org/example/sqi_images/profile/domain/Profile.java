package org.example.sqi_images.profile.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.employee.domain.Employee;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profile extends BaseEntity {

    @Lob
    @Column(nullable = false)
    private byte[] photo;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String part;

    @Column(nullable = false)
    private String languages;

    @Column(nullable = false)
    private String frameworks;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
