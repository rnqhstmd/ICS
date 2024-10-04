package org.example.sqi_images.common.dto.page.request;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PageRequestDto(int page) {

    private static final int PAGE_SIZE = 10;
    private static final String SORT_BY_CREATED_AT = "createdAt";
    private static final String SORT_BY_NAME = "name";
    private static final Sort.Direction SORT_DESC = Sort.Direction.DESC;
    private static final Sort.Direction SORT_ASC = Sort.Direction.ASC;


    public PageRequestDto(int page) {
        this.page = Math.max(page - 1, 0);
    }

    public Pageable toCurrentPageable() {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(SORT_DESC, SORT_BY_CREATED_AT));
    }

    public Pageable toNameAscPageable() {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(SORT_ASC, SORT_BY_NAME));
    }
}
