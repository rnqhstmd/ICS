package org.example.sqi_images.drive.aop.annotation;

import org.example.sqi_images.drive.domain.DriveAccessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDriveAccess {
    DriveAccessType[] accessType() default DriveAccessType.USER;
}
