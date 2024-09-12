package org.example.sqi_images.auth.utils;

import lombok.AllArgsConstructor;
import org.example.sqi_images.common.exception.UnauthorizedException;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.example.sqi_images.common.exception.type.ErrorType.INVALID_JWT_ERROR;

@AllArgsConstructor
public class JwtUtil {

    public static final String TOKEN_TYPE = "Bearer ";

    public static String encode(String token) {
        String cookieValue = TOKEN_TYPE + token;
        return URLEncoder.encode(cookieValue, StandardCharsets.UTF_8).replace("+", "%20");
    }

    public static String decode(String cookieValue) {
        String value = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
        if (value.startsWith(TOKEN_TYPE)) {
            return value.substring(TOKEN_TYPE.length());
        }
        throw new UnauthorizedException(INVALID_JWT_ERROR);
    }
}
