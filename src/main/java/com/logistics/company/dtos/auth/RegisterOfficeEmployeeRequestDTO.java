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
public class RegisterOfficeEmployeeRequestDTO extends RegisterUserRequestDTO implements Validatable {
    private Long officeId;

    @Override
    public boolean isInvalid() {
        return super.isInvalid() || !Validator.isIdValid(this.officeId, true);
    }
}
