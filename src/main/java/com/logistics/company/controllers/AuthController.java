package com.logistics.company.controllers;

import com.logistics.company.dtos.auth.AuthResponseDTO;
import com.logistics.company.dtos.auth.RegisterOfficeEmployeeRequestDTO;
import com.logistics.company.dtos.auth.RegisterUserRequestDTO;
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
    public ResponseEntity<AuthResponseDTO> registerClient(@RequestBody RegisterUserRequestDTO dto) {
        return ResponseEntity.ok(this.authService.registerClient(dto));
    }

    @PostMapping("register-courier-employee")
    public ResponseEntity<AuthResponseDTO> registerCourierEmployee(@RequestBody RegisterUserRequestDTO dto) {
        return ResponseEntity.ok(this.authService.registerCourierEmployee(dto));
    }

    @PostMapping("register-office-employee")
    public ResponseEntity<AuthResponseDTO> registerOfficeEmployee(@RequestBody RegisterOfficeEmployeeRequestDTO dto) {
        return ResponseEntity.ok(this.authService.registerOfficeEmployee(dto));
    }
}
