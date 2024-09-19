package org.example.sqi_images.auth.authentication.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.AuthenticationContext;
import org.example.sqi_images.auth.authentication.annotation.DepartmentCheck;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor
public class DepartmentCheckInterceptor implements HandlerInterceptor {

    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod && handlerMethod.hasMethodAnnotation(DepartmentCheck.class)) {
            Employee employee = authenticationContext.getPrincipal();
            Long requestedDepartmentId = Long.valueOf(request.getParameter("departmentId"));
            if (!employee.getDepartment().getId().equals(requestedDepartmentId)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("드라이브 접근 권한이 없습니다.");
                return false;
            }
        }
        return true;
    }
}
