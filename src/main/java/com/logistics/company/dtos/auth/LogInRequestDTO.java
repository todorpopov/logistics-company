package com.logistics.company.dtos.auth;

import com.logistics.company.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequestDTO {
    private String email;
    private String password;

    public boolean isInvalid() {
        return !Validator.isEmailValid(this.email)
            || !Validator.isStringValid(this.password, 8, 64);
    }
}
