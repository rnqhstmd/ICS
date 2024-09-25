package org.example.sqi_images.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;


@Getter
@RequiredArgsConstructor
public enum PartType {

    AI_EX,
    AI_HC,
    AI_VA,
    AI_SUP,

    SYS_EX,
    SYS_OSS,
    SYS_BSS,
    SYS_CNC,
    SYS_COM,

    EN_EX,
    EN_ZN,
    EN_SV,
    EN_PF,
    EN_PD,

    SC_EX,
    SC_SL,
    SC_IFR,
    SC_DEV,

    MD_EX,
    MD_PF,
    MD_BNS_1,
    MD_BNS_2,

    LAB_RS_1,
    LAB_RS_2,

    MAN_SUP,
    MAN_LAB;

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
