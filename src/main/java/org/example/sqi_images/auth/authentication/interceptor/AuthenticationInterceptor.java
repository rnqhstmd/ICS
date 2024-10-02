package org.example.sqi_images.auth.authentication.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.authentication.AccessTokenProvider;
import org.example.sqi_images.auth.authentication.AuthenticationContext;
import org.example.sqi_images.auth.authentication.AuthenticationExtractor;
import org.example.sqi_images.common.exception.UnauthorizedException;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.service.EmployeeQueryService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final EmployeeQueryService employeeQueryService;
    private final AuthenticationContext authenticationContext;
    private final AccessTokenProvider accessTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            // JWT 토큰 추출 및 유효성 검사
            String accessToken = AuthenticationExtractor.extract(request);
            Long employeeId = Long.valueOf(accessTokenProvider.getPayload(accessToken));

            // employeeId로 사용자 정보 조회 후 컨텍스트에 저장
            Employee employee = employeeQueryService.findExistingEmployee(employeeId);
            authenticationContext.setPrincipal(employee);
            return true;
        } catch (UnauthorizedException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return false;
        }
    }
}
