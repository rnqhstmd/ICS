package org.example.sqi_images.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "SQISOFT 사원정보 조회 서비스 API 명세서",
                description = "SQISOFT 사원정보 조회 서비스 API 명세서"))
@Configuration
public class SwaggerConfig {
}