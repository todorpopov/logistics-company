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
public class CreateUserRequestDTO implements Validatable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long officeId;

    private Boolean officeEmployee;

    public boolean isInvalid() {
        return !Validator.isEmailValid(this.email, true)
            || !Validator.isPasswordValid(this.password, true)
            || !Validator.isStringValid(this.firstName, 3, 255, true)
            || !Validator.isStringValid(this.lastName, 3, 255, true)
            || !Validator.isIdValid(this.officeId, this.officeEmployee);
    }
}
