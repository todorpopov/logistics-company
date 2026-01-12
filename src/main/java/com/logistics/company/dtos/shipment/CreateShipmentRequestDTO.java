package com.logistics.company.dtos.shipment;
import com.logistics.company.dtos.Validatable;
import com.logistics.company.models.enums.ShipmentDeliveryType;

import java.math.BigDecimal;

import com.logistics.company.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateShipmentRequestDTO implements Validatable {
    private Long senderId;
    private Long registeredById;
    private Long deliveryOfficeId;
    private Long courierEmployeeId;
    private ShipmentDeliveryType deliveryType;
    private String clientPhoneNumber;
    private Integer weightGram;
    private BigDecimal price;

    public boolean isInvalid() {
        return !Validator.isShipmentTypeValid(this.deliveryType, this.deliveryOfficeId, this.courierEmployeeId)
            || !Validator.isIdValid(this.senderId, true)
            || !Validator.isIdValid(this.registeredById, true)
            || !Validator.isIdValid(this.deliveryOfficeId, false)
            || !Validator.isIdValid(this.courierEmployeeId, false)
            || !Validator.isPhoneNumberValid(this.clientPhoneNumber,true)
            || !Validator.isWeightValid(this.weightGram, true)
            || !Validator.isPriceValid(this.price, true)
            || this.deliveryType == null;
    }
}
