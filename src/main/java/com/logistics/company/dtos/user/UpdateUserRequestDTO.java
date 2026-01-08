package com.logistics.company.dtos.user;

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
public class UpdateUserRequestDTO implements Validatable {
    private String firstName;
    private String lastName;
    private String email;

    public boolean isInvalid() {
        return !Validator.isStringValid(this.firstName, 3, 255, true)
            || !Validator.isStringValid(this.lastName, 3, 255, true)
            || !Validator.isEmailValid(this.email, true);
    }
}
