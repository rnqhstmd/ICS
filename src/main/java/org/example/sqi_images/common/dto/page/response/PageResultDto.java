package org.example.sqi_images.common.dto.page.response;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageResultDto<D, E>(
        List<D> data,
        int totalPage,
        long totalElements,
        int currentPage,
        int size
) {
    public PageResultDto(Page<E> result, Function<E, D> entityToDtoFunction) {
        this(
                result.getContent().stream().map(entityToDtoFunction).toList(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber(),
                result.getSize()
        );
    }
}
