package org.example.sqi_images.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginDto(
        @NotBlank(message = "사내 이메일을 입력해주세요.")
        @Pattern(regexp = "^[a-z]+\\.[a-z]+$", message = "형식이 올바르지 않습니다.")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$", message = "비밀번호는 영문과 숫자,특수기호를 조합하여 8~20글자 미만으로 입력해주세요.")
        String password
) {
}
