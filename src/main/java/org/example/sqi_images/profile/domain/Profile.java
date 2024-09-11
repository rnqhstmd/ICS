package org.example.sqi_images.profile.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.BaseEntity;

@Entity
@Getter
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
}
