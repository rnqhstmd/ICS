package org.example.sqi_images.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.dto.request.LoginDto;
import org.example.sqi_images.auth.dto.request.RegisterDto;
import org.example.sqi_images.auth.dto.response.TokenDto;
import org.example.sqi_images.auth.service.AuthService;
import org.example.sqi_images.auth.utils.CookieUtil;
import org.example.sqi_images.auth.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto registerDto) {
        authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = authService.login(loginDto);
        String encodedToken = JwtUtil.encode(tokenDto.accessToken());
        CookieUtil.setCookie(response, encodedToken);
        return ResponseEntity.ok().build();
    }
}
