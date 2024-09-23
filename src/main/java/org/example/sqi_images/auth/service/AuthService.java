package org.example.sqi_images.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.dto.request.LoginDto;
import org.example.sqi_images.auth.dto.response.TokenDto;
import org.example.sqi_images.auth.utils.PasswordHashEncryption;
import org.example.sqi_images.auth.authentication.AccessTokenProvider;
import org.example.sqi_images.common.exception.ConflictException;
import org.example.sqi_images.common.exception.NotFoundException;
import org.example.sqi_images.common.exception.UnauthorizedException;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.auth.dto.request.RegisterDto;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import static org.example.sqi_images.common.constant.Constants.SQISOFT_EMAIL;
import static org.example.sqi_images.common.exception.type.ErrorType.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordHashEncryption passwordHashEncryption;
    private final AccessTokenProvider accessTokenProvider;

    public void register(RegisterDto registerDto) {
        String name = registerDto.name();
        validateIsDuplicatedName(name);

        String email = registerDto.email() + SQISOFT_EMAIL;
        validateIsDuplicatedEmail(email);

        String plainPassword = registerDto.password();
        String encryptedPassword = passwordHashEncryption.encrypt(plainPassword);

        Employee newEmployee = new Employee(email, encryptedPassword, name);
        employeeRepository.save(newEmployee);
    }

    public TokenDto login(LoginDto loginDto) {
        Employee employee = findExistingUserByEmail(loginDto.email() + SQISOFT_EMAIL);

        validateIsPasswordMatches(loginDto.password(), employee.getPassword());

        String accessToken = accessTokenProvider.createToken(String.valueOf(employee.getId()));
        return new TokenDto(accessToken);
    }

    public Employee findExistingUserByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR));
    }

    public void validateIsPasswordMatches(String requestedPassword, String userPassword) {
        if (!passwordHashEncryption.matches(requestedPassword, userPassword)) {
            throw new UnauthorizedException(INVALID_CREDENTIALS_ERROR);
        }
    }

    public void validateIsDuplicatedName(String name) {
        if (employeeRepository.existsByName(name)) {
            throw new ConflictException(DUPLICATED_NAME);
        }
    }

    public void validateIsDuplicatedEmail(String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new ConflictException(DUPLICATED_EMAIL);
        }
    }
}
