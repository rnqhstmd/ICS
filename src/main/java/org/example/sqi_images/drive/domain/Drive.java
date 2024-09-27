package org.example.sqi_images.drive.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private String driveName;

    @OneToMany(mappedBy = "drive")
    private List<DriveEmployee> driveEmployees;

    @OneToMany(mappedBy = "drive")
    private List<FileInfo> fileInfos;

    public Drive(String driveName) {
        this.driveName = driveName;
    }
}
