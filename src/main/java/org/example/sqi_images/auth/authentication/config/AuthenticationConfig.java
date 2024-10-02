package org.example.sqi_images.auth.authentication.config;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.argumentresolver.AuthenticatedEmployeeArgumentResolver;
import org.example.sqi_images.auth.authentication.interceptor.AdminRoleInterceptor;
import org.example.sqi_images.auth.authentication.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.example.sqi_images.common.constant.Constants.*;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminRoleInterceptor adminRoleInterceptor;
    private final AuthenticatedEmployeeArgumentResolver authenticatedEmployeeArgumentResolver;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        addAuthenticationInterceptor(registry);
        addAdminRoleInterceptor(registry);
    }

    private void addAuthenticationInterceptor(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns(ADD_ADMIN_API_PATH)
                .addPathPatterns(ADD_AUTH_API_PATH)
                .addPathPatterns(ADD_PROFILE_API_PATH)
                .addPathPatterns(ADD_DRIVE_API_PATH)
                .excludePathPatterns(EXCLUDE_AUTH_API_PATH)
                .order(1);
    }

    private void addAdminRoleInterceptor(final InterceptorRegistry registry) {
        registry.addInterceptor(adminRoleInterceptor)
                .addPathPatterns(ADD_ADMIN_API_PATH)
                .order(2);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedEmployeeArgumentResolver);
    }
}

