package com.logistics.company.dtos.user;

import com.logistics.company.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InternalEmployeeDTO {
    private Long userId;
    private Long officeEmployeeId;
    private Long courierEmployeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Long officeId;
    private UserRole userRole;
}
