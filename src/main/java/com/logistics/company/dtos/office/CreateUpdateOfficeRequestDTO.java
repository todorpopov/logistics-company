package com.logistics.company.dtos.office;

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
public class CreateUpdateOfficeRequestDTO implements Validatable {
    private String name;
    private String address;
    private String phoneNumber;

    public void validate() throws BadRequestException {
        HashMap<String, String> errors = new HashMap<>();

        String nameValidation = Validator.isStringValid(this.name, 1, 255, true);
        if (!nameValidation.isEmpty()) {
            errors.put("name", nameValidation);
        }
        String addressValidation = Validator.isStringValid(this.address, 1, 255, true);
        if (!addressValidation.isEmpty()) {
            errors.put("address", addressValidation);
        }
        String phoneNumberValidation = Validator.isPhoneNumberValid(this.phoneNumber, true);
        if (!phoneNumberValidation.isEmpty()) {
            errors.put("phoneNumber", phoneNumberValidation);
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Invalid request", errors);
        }
    }
}
