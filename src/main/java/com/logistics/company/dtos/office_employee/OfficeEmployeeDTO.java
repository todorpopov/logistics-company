package com.logistics.company.dtos.office_employee;

import com.logistics.company.dtos.office.OfficeDTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfficeEmployeeDTO {
    private Long userId;
    private Long officeEmployeeId;
    private String firstName;
    private String lastName;
    private String email;
    private OfficeDTO office;
}
