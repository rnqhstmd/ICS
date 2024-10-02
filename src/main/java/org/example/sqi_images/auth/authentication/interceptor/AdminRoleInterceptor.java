package org.example.sqi_images.auth.authentication.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.AuthenticationContext;
import org.example.sqi_images.auth.authentication.annotation.Admin;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

import static org.example.sqi_images.employee.domain.EmployeeRole.ADMIN;

@Component
@RequiredArgsConstructor
public class AdminRoleInterceptor implements HandlerInterceptor {

    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Admin adminAnnotation = handlerMethod.getMethodAnnotation(Admin.class);

        // Admin 어노테이션이 없으면 권한 체크 필요 없음
        if (adminAnnotation == null) {
            return true;
        }

        // Admin 어노테이션이 있는 경우, 관리자 권한 체크
        return checkAdminRole(response);
    }

    private boolean checkAdminRole(HttpServletResponse response) throws IOException {
        Employee employee = authenticationContext.getPrincipal();

        // 관리자 권한이 아닌 경우 처리
        if (employee.getRole() != ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 작업은 시스템 관리자 권한이 필요합니다.");
            return false;
        }

        return true;
    }
}
