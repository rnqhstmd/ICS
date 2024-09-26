package org.example.sqi_images.auth.authentication.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.AccessTokenProvider;
import org.example.sqi_images.auth.authentication.AuthenticationContext;
import org.example.sqi_images.auth.authentication.AuthenticationExtractor;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.service.EmployeeService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final EmployeeService employeeService;
    private final AuthenticationContext authenticationContext;
    private final AccessTokenProvider accessTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnsupportedEncodingException {
        String accessToken = AuthenticationExtractor.extract(request);
        Long employeeId = Long.valueOf(accessTokenProvider.getPayload(accessToken));
        Employee employee = employeeService.findExistingEmployee(employeeId);
        authenticationContext.setPrincipal(employee);
        return true;
    }
}
