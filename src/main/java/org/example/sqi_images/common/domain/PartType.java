package org.example.sqi_images.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;


@Getter
@RequiredArgsConstructor
public enum PartType {

    AI_HEALTH_CARE("헬스케어"),
    AI_VIDEO_ANALYSIS("영상분석"),
    AI_SUPPORT("지원");

    private final String description;

    @JsonCreator
    public static PartType fromValue(String value) {
        return Stream.of(PartType.values())
                .filter(p -> p.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown PartType: " + value + ", Allowed values are " + Arrays.toString(values())
                ));
    }
}
