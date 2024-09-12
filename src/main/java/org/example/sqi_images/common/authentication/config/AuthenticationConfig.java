package org.example.sqi_images.common.authentication.config;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.authentication.AuthenticatedEmployeeArgumentResolver;
import org.example.sqi_images.common.authentication.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthenticatedEmployeeArgumentResolver authenticatedEmployeeArgumentResolver;
    private static final String ADD_AUTH_API_PATH = "/api/auth/logout";
    private static final String ADD_PROFILE_API_PATH = "/api/profiles/**";
    private static final String[] EXCLUDE_AUTH_API_PATH = {"/api/auth/login", "/api/auth/sign-up"};

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns(ADD_AUTH_API_PATH)
                .addPathPatterns(ADD_PROFILE_API_PATH)
                .excludePathPatterns(EXCLUDE_AUTH_API_PATH);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedEmployeeArgumentResolver);
    }
}

