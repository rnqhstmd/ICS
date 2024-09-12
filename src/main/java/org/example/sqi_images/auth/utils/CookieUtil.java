package org.example.sqi_images.auth.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.example.sqi_images.common.authentication.AuthenticationExtractor;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public final class CookieUtil {

    private CookieUtil() {

    }

    public static void setCookie(HttpServletResponse response, String encodedToken) {
        ResponseCookie cookie = ResponseCookie.from(AuthenticationExtractor.TOKEN_COOKIE_NAME, encodedToken)
                .maxAge(Duration.ofMillis(1800000))
                .path("/")
                .httpOnly(true)
                .sameSite("None").secure(true)
                .build();

        response.addHeader("set-cookie", cookie.toString());
    }
}
