package org.example.sqi_images.file.domain;

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
@Table(name = "file_data")
public class FileData extends BaseEntity {

    @Lob
    @Column(nullable = false)
    private byte[] fileBytes;

    @Column(nullable = false)
    private String fileContentType;

    @Column(nullable = false)
    private long fileSize;
}
