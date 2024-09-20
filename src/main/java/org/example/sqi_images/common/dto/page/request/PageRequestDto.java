package org.example.sqi_images.common.dto.page.request;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PageRequestDto(int page) {

    private static final int PAGE_SIZE = 10;
    private static final String SORT_BY = "createdAt";
    private static final Sort.Direction SORT_DIRECTION = Sort.Direction.DESC;


    public PageRequestDto(int page) {
        this.page = Math.max(page - 1, 0);
    }

    public Pageable toPageable() {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(SORT_DIRECTION, SORT_BY));
    }
}
