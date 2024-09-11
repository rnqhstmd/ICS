package org.example.sqi_images.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.auth.utils.PasswordHashEncryption;
import org.example.sqi_images.common.exception.ConflictException;
import org.example.sqi_images.common.exception.type.ErrorType;
import org.example.sqi_images.employee.domain.Employee;
import org.example.sqi_images.auth.dto.RegisterDto;
import org.example.sqi_images.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import static org.example.sqi_images.common.exception.type.ErrorType.DUPLICATED_EMAIL;
import static org.example.sqi_images.common.exception.type.ErrorType.DUPLICATED_NAME;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordHashEncryption passwordHashEncryption;

    public void register(RegisterDto registerDto) {
        String name = registerDto.getName();
        validateIsDuplicatedName(name);

        String email = registerDto.getEmail() + "@sqisoft.com";
        validateIsDuplicatedEmail(email);

        String plainPassword = registerDto.getPassword();
        String encryptedPassword = passwordHashEncryption.encrypt(plainPassword);

        Employee newEmployee = new Employee(email, encryptedPassword, name);
        employeeRepository.save(newEmployee);
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
