package org.example.sqi_images.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.stream.Stream;


public enum FrameworkType {
    SPRING,
    REACT;

    @JsonCreator
    public static FrameworkType fromValue(String value) {
        return Stream.of(FrameworkType.values())
                .filter(f -> f.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown FrameworkType: " + value + ", Allowed values are " + Arrays.toString(values())
                ));
    }
}
