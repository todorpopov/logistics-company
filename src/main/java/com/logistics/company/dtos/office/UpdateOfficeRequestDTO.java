package com.logistics.company.dtos.office;

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
public class UpdateOfficeRequestDTO implements Validatable {
    private Long officeId;
    private String name;
    private String address;
    private String phoneNumber;

    public boolean isInvalid() {
        return !Validator.isIdValid(this.officeId, true)
            || !Validator.isStringValid(this.name, 3, 255, false)
            || !Validator.isStringValid(this.address, 3, 255, false)
            || !Validator.isPhoneNumberValid(this.phoneNumber, false);
    }
}
