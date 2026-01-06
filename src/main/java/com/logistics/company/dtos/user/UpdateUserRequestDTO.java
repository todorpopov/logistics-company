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
    private Long userId;
    private Long entityId;
    private String firstName;
    private String lastName;
    private String email;

    public boolean isInvalid() {
        if (this.entityId == null && this.userId == null) {
            return true;
        }
        if ((this.entityId != null && this.userId != null) && !this.entityId.equals(this.userId)) {
            return true;
        }
        return !Validator.isIdValid(this.entityId, false)
            || !Validator.isIdValid(this.userId, false)
            || !Validator.isStringValid(this.firstName, 3, 255, false)
            || !Validator.isStringValid(this.lastName, 3, 255, false)
            || !Validator.isEmailValid(this.email, false);
    }
}
