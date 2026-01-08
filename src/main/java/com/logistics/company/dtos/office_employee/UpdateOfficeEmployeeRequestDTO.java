package com.logistics.company.dtos.office_employee;

import com.logistics.company.dtos.Validatable;
import com.logistics.company.dtos.user.UpdateUserRequestDTO;
import com.logistics.company.util.Validator;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOfficeEmployeeRequestDTO extends UpdateUserRequestDTO implements Validatable {
    private Long officeId;

    public boolean isInvalid() {
        return super.isInvalid() || !Validator.isIdValid(this.officeId, true);
    }
}
