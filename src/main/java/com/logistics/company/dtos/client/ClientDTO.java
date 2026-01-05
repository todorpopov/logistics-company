package com.logistics.company.dtos.client;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long userId;
    private Long clientId;
    private String firstName;
    private String lastName;
    private String email;
}
