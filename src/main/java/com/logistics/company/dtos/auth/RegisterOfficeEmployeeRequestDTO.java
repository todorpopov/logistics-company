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
public class RegisterOfficeEmployeeRequestDTO extends RegisterUserRequestDTO {
    private Long officeId;

    @Override
    public boolean isValid() {
        return super.isValid() && Validator.isIdValid(this.officeId);
    }
}
