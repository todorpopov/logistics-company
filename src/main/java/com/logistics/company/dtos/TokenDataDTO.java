package com.logistics.company.dtos;

import com.logistics.company.models.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDataDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
}
