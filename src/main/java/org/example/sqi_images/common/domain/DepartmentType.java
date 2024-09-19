package org.example.sqi_images.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.NotFoundException;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.example.sqi_images.common.exception.type.ErrorType.DEPARTMENT_NOT_FOUND_ERROR;

@Getter
@RequiredArgsConstructor
public enum DepartmentType {

    AI("AI사업부"),
    SYSTEM("시스템사업부"),
    ENERGY("에너지사업부"),
    SECURITY("보안사업부"),
    MEDIA("미디어사업부"),
    LAB("연구실"),
    MANAGEMENT("경영지원부");

    private final String description;

    @JsonCreator // JSON 값에서 enum 인스턴스를 생성하기 위한 메서드
    public static DepartmentType fromValue(String value) {
        for (DepartmentType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
}
