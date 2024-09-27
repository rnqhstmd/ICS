package org.example.sqi_images.common.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    // BAD_REQUEST
    INVALID_PAGE_REQUEST_ERROR(40000, "요청한 페이지 번호가 유효하지 않습니다."),
    UPLOADED_FILE_EMPTY_ERROR(40001, "업로드된 파일이 비어있습니다."),
    DUPLICATED_FILE_NAME_ERROR(40002, "이미 존재하는 파일 이름입니다."),

    // UNAUTHORIZED
    NOT_AUTHENTICATED_ERROR(40100, "인증되지 않았습니다."),
    INVALID_CREDENTIALS_ERROR(40101, "아이디나 비밀번호가 일치하지 않습니다."),
    JWT_NOT_FOUND_ERROR(40102, "인증 정보가 없습니다."),
    COOKIE_NOT_FOUND_ERROR(40103, "쿠키 정보가 없습니다."),
    INVALID_JWT_ERROR(40104, "인증 정보가 유효하지 않습니다."),
    TOKEN_EXPIRED_ERROR(40105, "인증에 필요한 토큰이 만료되었습니다."),

    // FORBIDDEN
    NO_DRIVE_ACCESS_ERROR(40300, "해당 드라이브의 접근 권한이 없습니다."),
    NO_ADMIN_ACCESS_ERROR(40301, "해당 작업은 관리자 권한이 필요합니다."),

    // NOT_FOUND
    NO_RESOURCE_ERROR(40400, "해당 리소스를 찾을 수 없습니다."),
    EMPLOYEE_NOT_FOUND_ERROR(40401, "해당 사원을 찾을 수 없습니다."),
    PROFILE_NOT_FOUND_ERROR(40402, "해당 프로필을 찾을 수 없습니다."),
    PHOTO_NOT_FOUND_ERROR(40403, "해당 이미지를 찾을 수 없습니다."),
    FILE_NOT_FOUND_ERROR(40404, "해당 파일을 찾을 수 없습니다."),
    DEPARTMENT_NOT_FOUND_ERROR(40405,"해당 부서를 찾을 수 없습니다."),
    PART_NOT_FOUND_ERROR(40406, "해당 파트를 찾을 수 없습니다"),
    DRIVE_NOT_FOUND_ERROR(40407, "해당 드라이브를 찾을 수 없습니다."),

    // HTTP
    METHOD_NOT_ALLOWED_ERROR(40500, "잘못된 HTTP 메서드입니다."),

    // MULTIPART
    MEDIA_TYPE_NOT_SUPPORTED_ERROR(41500, "Content-Type 헤더가 지원되지 않는 미디어 타입을 지정하고 있습니다."),
    MEDIA_TYPE_NOT_ACCEPTABLE_ERROR(40600, "Accept 헤더가 지원되지 않는 미디어 타입을 지정하고 있습니다."),
    UPLOAD_FILE_SIZE_LIMIT_EXCEEDED(41300, "허용된 파일 크기를 초과했습니다."),

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
