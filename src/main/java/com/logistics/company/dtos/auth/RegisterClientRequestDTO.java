package com.logistics.company.dtos.auth;

import com.logistics.company.dtos.Validatable;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClientRequestDTO implements Validatable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public void validate() throws BadRequestException {
        HashMap<String, String> errors = new HashMap<>();

        String emailValidation = Validator.isEmailValid(this.email, true);
        if (!emailValidation.isEmpty()) {
            errors.put("email", emailValidation);
        }

        String passwordValidation = Validator.isPasswordValid(this.password, true);
        if (!passwordValidation.isEmpty()) {
            errors.put("password", passwordValidation);
        }

        String firstNameValidation = Validator.isStringValid(this.firstName, 1, 255, true);
        if (!firstNameValidation.isEmpty()) {
            errors.put("firstName", firstNameValidation);
        }

        String lastNameValidation = Validator.isStringValid(this.lastName, 1, 255, true);
        if (!lastNameValidation.isEmpty()) {
            errors.put("lastName", lastNameValidation);
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Invalid request", errors);
        }
    }
}
