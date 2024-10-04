package org.example.sqi_images.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.dto.request.LoginDto;
import org.example.sqi_images.auth.dto.request.RegisterDto;
import org.example.sqi_images.auth.dto.response.TokenDto;
import org.example.sqi_images.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.sqi_images.auth.utils.CookieUtil.clearCookies;
import static org.example.sqi_images.auth.utils.CookieUtil.setCookie;
import static org.example.sqi_images.auth.utils.JwtUtil.encode;

@Tag(name = "Auth", description = "권한 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "새로운 사원을 생성합니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto registerDto) {
        authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "로그인", description = "사원을 로그인시킵니다.")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = authService.login(loginDto);
        String encodedToken = encode(tokenDto.accessToken());
        setCookie(response, encodedToken);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그아웃", description = "사원을 로그아웃시킵니다.")
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(final HttpServletResponse response) {
        clearCookies(response);
        return ResponseEntity.ok().build();
    }
}
