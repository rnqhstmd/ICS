package org.example.sqi_images.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank(message = "사내 이메일을 입력해주세요.")
        @Pattern(regexp = "^[a-z]+\\.[a-z]+$", message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "영문과 숫자,특수기호를 조합하여 8 ~ 20글자로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$", message = "비밀번호는 영문과 숫자,특수기호를 조합하여 8~20글자 미만으로 입력해주세요.")
        String password,

        @NotBlank(message = "사용하실 이름을 입력해주세요.")
        @Size(min = 1, max = 10, message = "이름은 최소 1자에서 최대 10자까지 입력 가능합니다.")
        String name
) {
}