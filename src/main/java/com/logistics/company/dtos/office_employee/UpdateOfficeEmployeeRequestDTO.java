package com.logistics.company.dtos.office_employee;

import com.logistics.company.dtos.Validatable;
import com.logistics.company.dtos.user.UpdateUserRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.util.Validator;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOfficeEmployeeRequestDTO extends UpdateUserRequestDTO implements Validatable {
    private Long officeId;

    public void validate() throws BadRequestException {
        try {
            super.validate();
        } catch (BadRequestException e) {
            String officeIdValidation = Validator.isIdValidMsg(this.officeId, true);
            if (!officeIdValidation.isEmpty()) {
                e.setError("officeId", officeIdValidation);
            }
            throw e;
        }
    }
}
