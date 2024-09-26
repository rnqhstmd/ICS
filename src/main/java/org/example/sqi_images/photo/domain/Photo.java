package org.example.sqi_images.photo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.employee.domain.EmployeeDetail;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "photos")
public class Photo extends BaseEntity {

    @Lob
    @Column(nullable = false)
    private byte[] photoData;

    @OneToOne(mappedBy = "photo")
    private EmployeeDetail detail;

    public Photo(byte[] photoData) {
        this.photoData = photoData;
    }
}
