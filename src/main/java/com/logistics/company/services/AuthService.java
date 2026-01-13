package com.logistics.company.services;

import com.logistics.company.dtos.auth.AuthResponseDTO;
import com.logistics.company.dtos.auth.LogInRequestDTO;
import com.logistics.company.dtos.auth.RegisterClientRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.exceptions.custom.UnauthorizedException;
import com.logistics.company.models.*;
import com.logistics.company.models.enums.UserRole;
import com.logistics.company.repositories.*;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class.getName());
    private final String adminEmail;
    private final String adminPassword;

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public AuthService(
        JwtService jwtService,
        UserRepository userRepository,
        ClientRepository clientRepository,
        @Value("${admin.email}") String adminEmail,
        @Value("${admin.password}") String adminPassword
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Transactional
    public AuthResponseDTO registerClient(RegisterClientRequestDTO dto) {
        dto.validate();

        User user = User.builder()
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .email(dto.getEmail())
            .passwordHash(BcryptUtils.hashPassword(dto.getPassword()))
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

    public AuthResponseDTO logUserIn(LogInRequestDTO dto) {
        dto.validate();

        try {
            User user = this.userRepository.findByEmail(dto.getEmail()).orElseThrow();
            if (!BcryptUtils.checkPassword(dto.getPassword(), user.getPasswordHash())) {
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

    public AuthResponseDTO logAdminIn(LogInRequestDTO dto) {
        dto.validate();

        if (dto.getEmail().equals(adminEmail) && dto.getPassword().equals(adminPassword)) {
            return this.generateAuthResponse(
                1L,
                "Admin",
                "Admin",
                dto.getEmail(),
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
