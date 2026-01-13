package com.logistics.company.dtos.user;

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
public class UpdateUserRequestDTO implements Validatable {
    private String firstName;
    private String lastName;
    private String email;

    public void validate() throws BadRequestException {
        HashMap<String, String> errors = new HashMap<>();

        String firstNameValidation = Validator.isStringValid(this.firstName, 1, 255, true);
        if (!firstNameValidation.isEmpty()) {
            errors.put("firstName", firstNameValidation);
        }

        String lastNameValidation = Validator.isStringValid(this.lastName, 1, 255, true);
        if (!lastNameValidation.isEmpty()) {
            errors.put("lastName", lastNameValidation);
        }

        String emailValidation = Validator.isEmailValid(this.email, true);
        if (!emailValidation.isEmpty()) {
            errors.put("email", emailValidation);
        }


        if (!errors.isEmpty()) {
            throw new BadRequestException("Invalid request", errors);
        }
    }
}
