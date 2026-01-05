package com.logistics.company.services;

import com.logistics.company.dtos.auth.AuthResponseDTO;
import com.logistics.company.dtos.auth.LogInRequestDTO;
import com.logistics.company.dtos.auth.RegisterOfficeEmployeeRequestDTO;
import com.logistics.company.dtos.auth.RegisterUserRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.exceptions.custom.UnauthorizedException;
import com.logistics.company.models.*;
import com.logistics.company.models.enums.UserRole;
import com.logistics.company.repositories.*;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class.getName());
    private final String adminEmail;
    private final String adminPassword;

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final CourierEmployeeRepository courierEmployeeRepository;
    private final OfficeEmployeeRepository officeEmployeeRepository;
    private final OfficeRepository officeRepository;

    public AuthService(
        JwtService jwtService,
        UserRepository userRepository,
        ClientRepository clientRepository,
        CourierEmployeeRepository courierEmployeeRepository,
        OfficeEmployeeRepository officeEmployeeRepository,
        OfficeRepository officeRepository,
        @Value("${admin.email}") String adminEmail,
        @Value("${admin.password}") String adminPassword
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.courierEmployeeRepository = courierEmployeeRepository;
        this.officeEmployeeRepository = officeEmployeeRepository;
        this.officeRepository = officeRepository;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Transactional
    public AuthResponseDTO registerClient(RegisterUserRequestDTO registerUserRequestDTO) {
        if (registerUserRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        User user = User.builder()
            .firstName(registerUserRequestDTO.getFirstName())
            .lastName(registerUserRequestDTO.getLastName())
            .email(registerUserRequestDTO.getEmail())
            .passwordHash(BcryptUtils.hashPassword(registerUserRequestDTO.getPassword()))
            .userRole(UserRole.CLIENT)
            .build();

        Client client = Client.builder()
            .user(user)
            .build();

        try {
            this.clientRepository.save(client);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }

        return this.generateAuthResponse(
            user.getUserId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getUserRole()
        );
    }

    @Transactional
    public AuthResponseDTO registerCourierEmployee(RegisterUserRequestDTO registerUserRequestDTO) {
        if (registerUserRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        User user = User.builder()
            .firstName(registerUserRequestDTO.getFirstName())
            .lastName(registerUserRequestDTO.getLastName())
            .email(registerUserRequestDTO.getEmail())
            .passwordHash(BcryptUtils.hashPassword(registerUserRequestDTO.getPassword()))
            .userRole(UserRole.COURIER_EMPLOYEE)
            .build();

        CourierEmployee courierEmployee = CourierEmployee.builder()
            .user(user)
            .build();

        try {
            this.courierEmployeeRepository.save(courierEmployee);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }

        return this.generateAuthResponse(
            user.getUserId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getUserRole()
        );
    }

    @Transactional
    public AuthResponseDTO registerOfficeEmployee(RegisterOfficeEmployeeRequestDTO registerOfficeEmployeeRequestDTO) {
        if (registerOfficeEmployeeRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        User user = User.builder()
            .firstName(registerOfficeEmployeeRequestDTO.getFirstName())
            .lastName(registerOfficeEmployeeRequestDTO.getLastName())
            .email(registerOfficeEmployeeRequestDTO.getEmail())
            .passwordHash(BcryptUtils.hashPassword(registerOfficeEmployeeRequestDTO.getPassword()))
            .userRole(UserRole.OFFICE_EMPLOYEE)
            .build();

        try {
            Office office = this.officeRepository.findById(registerOfficeEmployeeRequestDTO.getOfficeId()).orElseThrow();
            OfficeEmployee officeEmployee = OfficeEmployee.builder()
                .user(user)
                .office(office)
                .build();
            this.officeEmployeeRepository.save(officeEmployee);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Office not found");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }

        return this.generateAuthResponse(
            user.getUserId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getUserRole()
        );
    }

    public AuthResponseDTO logUserIn(LogInRequestDTO logInRequestDTO) {
        if (logInRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        try {
            User user = this.userRepository.findByEmail(logInRequestDTO.getEmail()).orElseThrow();
            if (!BcryptUtils.checkPassword(logInRequestDTO.getPassword(), user.getPasswordHash())) {
                throw new UnauthorizedException("Invalid credentials");
            }
            return this.generateAuthResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserRole()
            );
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("User not found");
        } catch (DataAccessException | UnauthorizedException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public AuthResponseDTO logAdminIn(LogInRequestDTO logInRequestDTO) {
        if (logInRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        if (logInRequestDTO.getEmail().equals(adminEmail) && logInRequestDTO.getPassword().equals(adminPassword)) {
            return this.generateAuthResponse(
                1L,
                "Admin",
                "Admin",
                logInRequestDTO.getEmail(),
                UserRole.ADMIN
            );
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    private AuthResponseDTO generateAuthResponse(
        Long userId,
        String firstName,
        String lastName,
        String email,
        UserRole role)
    {
        String token = this.jwtService.generateToken(userId, firstName, lastName, email, role);
        return new AuthResponseDTO(userId, token, email, firstName, lastName, role);
    }
}
