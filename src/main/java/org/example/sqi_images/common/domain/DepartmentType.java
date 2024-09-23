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

    AI,
    SYSTEM,
    ENERGY,
    SECURITY,
    MEDIA,
    LAB,
    MANAGEMENT;

    @JsonCreator
    public static DepartmentType fromValue(String value) {
        for (DepartmentType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
}
