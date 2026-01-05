package com.logistics.company.dtos.courier_employee;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierEmployeeDTO {
    private Long userId;
    private Long courierEmployeeId;
    private String firstName;
    private String lastName;
    private String email;
}
