package org.example.sqi_images.file.domain.repository;

import org.example.sqi_images.file.domain.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    boolean existsByName(String fileName);

    Optional<FileInfo> findByIdAndDriveId(Long fileInfoId, Long driveId);

    @Query("SELECT f FROM FileInfo f " +
            "JOIN FETCH f.employee e " +
            "WHERE f.drive.id = :driveId AND f.isDeleted = false")
    Page<FileInfo> findByDriveIdWithFetchJoin(@Param("driveId") Long driveId, Pageable pageable);

    @Query("SELECT f FROM FileInfo f " +
            "JOIN FETCH f.employee e " +
            "WHERE f.drive.id = :driveId AND f.isDeleted = true")
    Page<FileInfo> findByDriveIdAndIsDeletedTrue(@Param("driveId") Long driveId, Pageable pageable);

    @Query("SELECT f FROM FileInfo f " +
            "WHERE f.isDeleted = true AND f.modifiedAt <= :thresholdDate")
    List<FileInfo> findFilesDeletedOlderThan(@Param("thresholdDate") LocalDateTime thresholdDate);
}
