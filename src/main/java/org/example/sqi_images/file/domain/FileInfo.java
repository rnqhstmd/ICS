package org.example.sqi_images.file.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.drive.domain.Drive;
import org.example.sqi_images.employee.domain.Employee;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file_infos")
public class FileInfo extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String formattedFileSize;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fileDataId")
    private FileData fileData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driveId")
    private Drive drive;

    public void updateIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
