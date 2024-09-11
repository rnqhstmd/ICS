package org.example.sqi_images.common.authentication;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.sqi_images.auth.utils.JwtUtil;
import org.example.sqi_images.common.exception.UnauthorizedException;
import static org.example.sqi_images.common.exception.type.ErrorType.COOKIE_NOT_FOUND_ERROR;


@Slf4j
public class AuthenticationExtractor {
    public static final String TOKEN_COOKIE_NAME = "AccessToken";

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
