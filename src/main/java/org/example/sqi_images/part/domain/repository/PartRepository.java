package org.example.sqi_images.part.domain.repository;

import org.example.sqi_images.common.domain.PartType;
import org.example.sqi_images.part.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findByPartType(PartType partType);
}
