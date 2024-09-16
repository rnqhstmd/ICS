package org.example.sqi_images.auth.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.UnsupportedEncodingException;

import static org.example.sqi_images.common.exception.type.ErrorType.EMPLOYEE_NOT_FOUND_ERROR;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final EmployeeRepository employeeRepository;
    private final AuthenticationContext authenticationContext;
    private final AccessTokenProvider accessTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnsupportedEncodingException {
        String accessToken = AuthenticationExtractor.extract(request);
        Long employeeId = Long.valueOf(accessTokenProvider.getPayload(accessToken));
        Employee employee = findExistingEmployee(employeeId);
        authenticationContext.setPrincipal(employee);
        return true;
    }

    private Employee findExistingEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR));
    }
}
