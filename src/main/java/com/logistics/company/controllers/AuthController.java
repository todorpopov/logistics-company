package com.logistics.company.controllers;

import com.logistics.company.dtos.auth.AuthResponseDTO;
import com.logistics.company.dtos.auth.LogInRequestDTO;
import com.logistics.company.dtos.auth.RegisterClientRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register-client")
    public ResponseEntity<AuthResponseDTO> registerClient(@RequestBody RegisterClientRequestDTO dto) {
        dto.validate();
        return ResponseEntity.ok(this.authService.registerClient(dto));
    }

    @PostMapping("log-in")
    public ResponseEntity<AuthResponseDTO> logUserIn(@RequestBody LogInRequestDTO dto) {
        dto.validate();
        return ResponseEntity.ok(this.authService.logUserIn(dto));
    }

    @PostMapping("log-admin-in")
    public ResponseEntity<AuthResponseDTO> logAdminIn(@RequestBody LogInRequestDTO dto) {
        dto.validate();
        return ResponseEntity.ok(this.authService.logAdminIn(dto));
    }
}
