package org.example.sqi_images.auth.authentication.annotation;

import org.example.sqi_images.employee.domain.EmployeeRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.example.sqi_images.employee.domain.EmployeeRole.ADMIN;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Admin {
    EmployeeRole role() default ADMIN;
}
