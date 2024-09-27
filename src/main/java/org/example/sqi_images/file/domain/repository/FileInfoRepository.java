package org.example.sqi_images.file.domain.repository;

import org.example.sqi_images.file.domain.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    boolean existsByFileName(String fileName);

    Optional<FileInfo> findByIdAndDriveId(Long fileInfoId, Long driveId);

    @Query("SELECT f FROM FileInfo f " +
            "JOIN FETCH f.employee e " +
            "WHERE f.drive.id = :driveId")
    Page<FileInfo> findByDriveIdWithFetchJoin(@Param("driveId") Long driveId, Pageable pageable);

}
