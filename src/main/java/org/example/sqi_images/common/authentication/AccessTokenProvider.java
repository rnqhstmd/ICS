package org.example.sqi_images.common.authentication;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.sqi_images.common.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.example.sqi_images.common.exception.type.ErrorType.INVALID_JWT_ERROR;

@Slf4j
@Component
public class AccessTokenProvider {
    private final SecretKey key; // AccessToken 시크릿 키
    private final long validityInMilliseconds; // AccessToken 유효 시간

    public AccessTokenProvider(@Value("${security.jwt.token.access-secret-key}") final String secretKey,
                               @Value("${security.jwt.token.access-expire-length}") final long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    // 토큰 생성
    public String createToken(final String payload) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 정보 추출
    public String getPayload(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            throw new UnauthorizedException(INVALID_JWT_ERROR);
        }
    }
}