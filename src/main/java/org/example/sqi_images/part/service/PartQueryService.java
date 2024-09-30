package org.example.sqi_images.part.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.domain.PartType;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.part.domain.Part;
import org.example.sqi_images.part.domain.repository.PartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.sqi_images.common.exception.type.ErrorType.PART_NOT_FOUND_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartQueryService {

    private final PartRepository partRepository;

    public Part findExistingPartByType(PartType partType) {
        return partRepository.findByPartType(partType)
                .orElseThrow(() -> new NotFoundException(PART_NOT_FOUND_ERROR));
    }
}
