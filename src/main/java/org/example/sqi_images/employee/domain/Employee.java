package org.example.sqi_images.employee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.profile.domain.Profile;

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

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Profile profile;

    public Employee(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile = Profile.builder()
                .employee(this)
                .build();
    }
}
