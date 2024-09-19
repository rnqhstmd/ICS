package org.example.sqi_images.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.stream.Stream;

public enum LanguageType {
    JAVA,
    JAVASCRIPT,
    PYTHON;

    @JsonCreator
    public static LanguageType fromValue(String value) {
        return Stream.of(LanguageType.values())
                .filter(l -> l.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown LanguageType: " + value + ", Allowed values are " + Arrays.toString(values())
                ));
    }
}
