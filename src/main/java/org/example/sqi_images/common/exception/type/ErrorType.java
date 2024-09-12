package org.example.sqi_images.common.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    // UNAUTHORIZED
    NOT_AUTHENTICATED_ERROR(40100, "인증되지 않았습니다."),
    INVALID_CREDENTIALS_ERROR(40101, "아이디나 비밀번호가 일치하지 않습니다."),
    JWT_NOT_FOUND_ERROR(40102, "인증 정보가 없습니다."),
    COOKIE_NOT_FOUND_ERROR(40103, "쿠키 정보가 없습니다."),
    INVALID_JWT_ERROR(40104, "인증 정보가 유효하지 않습니다."),
    TOKEN_EXPIRED_ERROR(40105, "인증에 필요한 토큰이 만료되었습니다."),

    // NOT_FOUND
    NO_RESOURCE_ERROR(40400, "해당 리소스를 찾을 수 없습니다."),
    EMPLOYEE_NOT_FOUND_ERROR(40401, "해당 사원을 찾을 수 없습니다."),

    // HTTP
    METHOD_NOT_ALLOWED_ERROR(40500, "잘못된 HTTP 메서드입니다."),

    // CONFLICT
    DUPLICATED_EMAIL(40900, "사용중인 이메일입니다."),
    DUPLICATED_NAME(40901, "사용중인 이름입니다."),
    PROFILE_ALREADY_EXISTS_ERROR(40902, "프로필이 이미 존재합니다."),

    // VALIDATION
    NOT_NULL_VALID_ERROR(90100, "필수값이 누락되었습니다."),
    NOT_BLANK_VALID_ERROR(90101, "필수값이 빈 값이거나 공백으로 되어있습니다."),
    REGEX_VALID_ERROR(90102, "형식에 맞지 않습니다."),
    LENGTH_VALID_ERROR(90103, "길이가 유효하지 않습니다.");

    private final int errorCode;
    private final String message;

    public static ErrorType resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL_VALID_ERROR;
            case "NotBlank" -> NOT_BLANK_VALID_ERROR;
            case "Pattern" -> REGEX_VALID_ERROR;
            case "Length" -> LENGTH_VALID_ERROR;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
