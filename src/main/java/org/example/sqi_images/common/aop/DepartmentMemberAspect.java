package org.example.sqi_images.common.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.sqi_images.auth.authentication.AuthenticationContext;
import org.example.sqi_images.common.aop.annotation.DepartmentMember;
import org.example.sqi_images.common.exception.ForbiddenException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.department.domain.repository.DepartmentRepository;
import org.example.sqi_images.drive.department.domain.DepartmentFile;
import org.example.sqi_images.drive.department.domain.repository.DepartmentFileRepository;
import org.example.sqi_images.employee.domain.Employee;
import org.springframework.stereotype.Component;

import static org.example.sqi_images.common.exception.type.ErrorType.*;

@Aspect
@Component
@RequiredArgsConstructor
public class DepartmentMemberAspect {

    private final AuthenticationContext authenticationContext;
    private final DepartmentFileRepository departmentFileRepository;
    private final DepartmentRepository departmentRepository;

    @Before("@annotation(departmentMember) && args(departmentId,..)")
    public void checkDepartmentMembership(DepartmentMember departmentMember, Long departmentId) {
        Employee employee = authenticationContext.getPrincipal();
        if (!departmentRepository.existsById(departmentId)) {
            throw new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR);
        }

        if (employee == null || !employee.getDepartment().getId().equals(departmentId)) {
            throw new ForbiddenException(DRIVE_ACCESS_DENIED_ERROR);
        }
    }

    @Before("@annotation(departmentMember) && args(departmentId, fileId,..)")
    public void checkDepartmentMembershipForFileAccess(DepartmentMember departmentMember, Long departmentId, Long fileId) {
        Employee employee = authenticationContext.getPrincipal();
        DepartmentFile file = departmentFileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(FILE_NOT_FOUND_ERROR));

        if (employee == null || !employee.getDepartment().getId().equals(departmentId) || !file.getDepartment().getId().equals(departmentId)) {
            throw new ForbiddenException(DRIVE_ACCESS_DENIED_ERROR);
        }
    }
}
