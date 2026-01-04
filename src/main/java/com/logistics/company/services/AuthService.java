package com.logistics.company.services;

import com.logistics.company.dtos.auth.AuthResponseDTO;
import com.logistics.company.dtos.auth.RegisterOfficeEmployeeRequestDTO;
import com.logistics.company.dtos.auth.RegisterUserRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.*;
import com.logistics.company.models.enums.UserRole;
import com.logistics.company.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class.getName());

    private final JwtService jwtService;
    private final ClientRepository clientRepository;
    private final CourierEmployeeRepository courierEmployeeRepository;
    private final OfficeEmployeeRepository officeEmployeeRepository;
    private final OfficeRepository officeRepository;

    public AuthService(
        JwtService jwtService,
        ClientRepository clientRepository,
        CourierEmployeeRepository courierEmployeeRepository,
        OfficeEmployeeRepository officeEmployeeRepository,
        OfficeRepository officeRepository
    ) {
        this.jwtService = jwtService;
        this.clientRepository = clientRepository;
        this.courierEmployeeRepository = courierEmployeeRepository;
        this.officeEmployeeRepository = officeEmployeeRepository;
        this.officeRepository = officeRepository;
    }

    @Transactional
    public AuthResponseDTO registerClient(RegisterUserRequestDTO registerUserRequestDTO) {
        if (!registerUserRequestDTO.isValid()) {
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

        return this.generateToken(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getUserRole()
        );
    }

    @Transactional
    public AuthResponseDTO registerCourierEmployee(RegisterUserRequestDTO registerUserRequestDTO) {
        if (!registerUserRequestDTO.isValid()) {
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

        return this.generateToken(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getUserRole()
        );
    }

    @Transactional
    public AuthResponseDTO registerOfficeEmployee(RegisterOfficeEmployeeRequestDTO registerOfficeEmployeeRequestDTO) {
        if (!registerOfficeEmployeeRequestDTO.isValid()) {
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

        return this.generateToken(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getUserRole()
        );
    }

    private AuthResponseDTO generateToken(Long userId, String firstName, String lastName, String email, UserRole role) {
        String token = this.jwtService.generateToken( userId, firstName, lastName, email, role);
        return new AuthResponseDTO( userId, token, email, firstName, lastName, role);
    }
}
