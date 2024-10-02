package org.example.sqi_images.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.dto.request.LoginDto;
import org.example.sqi_images.auth.dto.response.TokenDto;
import org.example.sqi_images.auth.utils.PasswordHashEncryption;
import org.example.sqi_images.auth.authentication.AccessTokenProvider;
import org.example.sqi_images.common.exception.UnauthorizedException;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.auth.dto.request.RegisterDto;
import org.example.sqi_images.employee.domain.repository.EmployeeRepository;
import org.example.sqi_images.employee.service.EmployeeQueryService;
import org.springframework.stereotype.Service;

import static org.example.sqi_images.common.constant.Constants.SQISOFT_EMAIL;
import static org.example.sqi_images.common.exception.type.ErrorType.*;
import static org.example.sqi_images.employee.domain.EmployeeRole.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordHashEncryption passwordHashEncryption;
    private final AccessTokenProvider accessTokenProvider;
    private final EmployeeQueryService employeeQueryService;

    public void register(RegisterDto registerDto) {
        String name = registerDto.name();
        employeeQueryService.validateIsDuplicatedName(name);

        String email = registerDto.email() + SQISOFT_EMAIL;
        employeeQueryService.validateIsDuplicatedEmail(email);

        String plainPassword = registerDto.password();
        String encryptedPassword = passwordHashEncryption.encrypt(plainPassword);

        Employee newEmployee = new Employee(email, encryptedPassword, name, USER);
        employeeRepository.save(newEmployee);
    }

    public TokenDto login(LoginDto loginDto) {
        Employee employee = employeeQueryService.findExistingUserByEmail(loginDto.email() + SQISOFT_EMAIL);

        validateIsPasswordMatches(loginDto.password(), employee.getPassword());

        String accessToken = accessTokenProvider.createToken(String.valueOf(employee.getId()));
        return new TokenDto(accessToken);
    }

    public void validateIsPasswordMatches(String requestedPassword, String userPassword) {
        if (!passwordHashEncryption.matches(requestedPassword, userPassword)) {
            throw new UnauthorizedException(INVALID_CREDENTIALS_ERROR);
        }
    }
}
