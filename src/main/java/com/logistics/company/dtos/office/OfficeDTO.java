package com.logistics.company.dtos.office;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDTO {
    private Long officeId;
    private String name;
    private String address;
    private String phoneNumber;
}
