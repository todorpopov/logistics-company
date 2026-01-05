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
public class LogInRequestDTO implements Validatable {
    private String email;
    private String password;

    public boolean isInvalid() {
        return !Validator.isEmailValid(this.email, true)
            || !Validator.isPasswordValid(this.password, true);
    }
}
