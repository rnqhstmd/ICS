package org.example.sqi_images.drive.global.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sqi_images.common.domain.BaseEntity;
import org.example.sqi_images.employee.domain.Employee;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "global_files")
public class GlobalFile extends BaseEntity {

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] fileData;

    @Column(nullable = false)
    private String fileContentType;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String formattedFileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
