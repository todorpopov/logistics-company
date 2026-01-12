package com.logistics.company.dtos.user;

import com.logistics.company.models.enums.UserRole;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Long userId;
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Long officeId;
    private UserRole userRole;
}
