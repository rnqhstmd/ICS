package org.example.sqi_images.drive.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.file.domain.FileInfo;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drives")
public class Drive extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "drive", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<DriveEmployee> driveEmployees;

    @OneToMany(mappedBy = "drive", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FileInfo> fileInfos;

    public Drive(String name) {
        this.name = name;
    }
}
