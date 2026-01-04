package com.logistics.company.dtos.auth;

import com.logistics.company.models.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private Long id;
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
}
