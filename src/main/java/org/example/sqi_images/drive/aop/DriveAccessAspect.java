package org.example.sqi_images.drive.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.sqi_images.auth.authentication.AuthenticationContext;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.drive.aop.annotation.CheckDriveAccess;
import org.example.sqi_images.drive.domain.DriveEmployee;
import org.example.sqi_images.drive.domain.repository.DriveEmployeeRepository;
import org.example.sqi_images.drive.service.DriveService;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.example.sqi_images.common.exception.type.ErrorType.NO_ADMIN_ACCESS_ERROR;

@Aspect
@Component
@RequiredArgsConstructor
public class DriveAccessAspect {

    private final DriveService driveService;
    private final AuthenticationContext authenticationContext;
    private final DriveEmployeeRepository driveEmployeeRepository;

    @Pointcut("@annotation(checkDriveAccess)")
    public void driveAccessCheckPointcut(CheckDriveAccess checkDriveAccess) {}

    @Around("driveAccessCheckPointcut(checkDriveAccess)")
    public Object verifyDriveAccess(ProceedingJoinPoint joinPoint, CheckDriveAccess checkDriveAccess) throws Throwable {
        // 현재 인증된 Employee 객체 가져오기
        Employee employee = authenticationContext.getPrincipal();
        Long employeeId = employee.getId();

        // 메서드 파라미터에서 driveId 추출
        Object[] args = joinPoint.getArgs();
        Long driveId = (Long) args[0];

        // 권한 체크 로직 수행
        DriveEmployee driveAccess = driveService.findExistingAccess(driveId, employeeId);

        if (Arrays.stream(checkDriveAccess.accessType())
                .noneMatch(requiredRole -> driveAccess.getRole() == requiredRole)) {
            throw new ForbiddenException(NO_ADMIN_ACCESS_ERROR);
        }
        return joinPoint.proceed();
    }
}
