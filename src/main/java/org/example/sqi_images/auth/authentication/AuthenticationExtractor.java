package org.example.sqi_images.auth.authentication;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.sqi_images.auth.utils.JwtUtil;
import org.example.sqi_images.common.exception.UnauthorizedException;

import static org.example.sqi_images.common.constant.Constants.TOKEN_COOKIE_NAME;
import static org.example.sqi_images.common.exception.type.ErrorType.COOKIE_NOT_FOUND_ERROR;


public class AuthenticationExtractor {

    private AuthenticationExtractor() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String extract(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return JwtUtil.decode(String.valueOf(cookie.getValue()));
                }
            }
        }
        throw new UnauthorizedException(COOKIE_NOT_FOUND_ERROR);
    }
}
