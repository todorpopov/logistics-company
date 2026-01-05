package com.logistics.company.dtos.auth;

import com.logistics.company.dtos.Validatable;
import com.logistics.company.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDTO implements Validatable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public boolean isInvalid() {
        return !Validator.isEmailValid(this.email, true)
            || !Validator.isPasswordValid(this.password, true)
            || !Validator.isStringValid(this.firstName, 0, 255, true)
            || !Validator.isStringValid(this.lastName, 0, 255, true);
    }
}
