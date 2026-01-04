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
public class RegisterUserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public boolean isInvalid() {
        return !Validator.isEmailValid(this.email)
            || !Validator.isStringValid(this.password, 8, 64)
            || !Validator.isStringValid(this.firstName, 0, 255)
            || !Validator.isStringValid(this.lastName, 0, 255);
    }
}
