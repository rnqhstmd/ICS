package org.example.sqi_images.common.authentication;

import lombok.Getter;
import lombok.Setter;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class AuthenticationContext {
    private Employee principal;
}
