package com.logistics.company.dtos.shipment;
import com.logistics.company.dtos.Validatable;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.enums.ShipmentDeliveryType;

import java.math.BigDecimal;
import java.util.HashMap;

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
    private Long receiverId;
    private Long registeredById;
    private Long deliveryOfficeId;
    private Long courierEmployeeId;
    private ShipmentDeliveryType deliveryType;
    private String clientPhoneNumber;
    private Integer weightGram;
    private BigDecimal price;

    public void validate() throws BadRequestException {
        HashMap<String, String> errors = new HashMap<>();

        String shipmentTypeValidation = Validator.isShipmentTypeValid(this.deliveryType, this.deliveryOfficeId, this.courierEmployeeId);
        if (!shipmentTypeValidation.isEmpty()) {
            errors.put("shipmentType", shipmentTypeValidation);
        }

        String senderIdValidation = Validator.isIdValidMsg(this.senderId, true);
        if (!senderIdValidation.isEmpty()) {
            errors.put("senderId", senderIdValidation);
        }

        String receiverIdValidation = Validator.isIdValidMsg(this.receiverId, true);
        if (!receiverIdValidation.isEmpty()) {
            errors.put("receiverId", receiverIdValidation);
        }

        String registeredByIdValidation = Validator.isIdValidMsg(this.registeredById, true);
        if (!registeredByIdValidation.isEmpty()) {
            errors.put("registeredById", registeredByIdValidation);
        }

        String deliveryOfficeIdValidation = Validator.isIdValidMsg(this.deliveryOfficeId, false);
        if (!deliveryOfficeIdValidation.isEmpty()) {
            errors.put("deliveryOfficeId", deliveryOfficeIdValidation);
        }

        String courierEmployeeIdValidation = Validator.isIdValidMsg(this.courierEmployeeId, false);
        if (!courierEmployeeIdValidation.isEmpty()) {
            errors.put("courierEmployeeId", courierEmployeeIdValidation);
        }

        String clientPhoneNumberValidation = Validator.isPhoneNumberValid(this.clientPhoneNumber, true);
        if (!clientPhoneNumberValidation.isEmpty()) {
            errors.put("clientPhoneNumber", clientPhoneNumberValidation);
        }

        String weightGramValidation = Validator.isWeightValid(this.weightGram, true);
        if (!weightGramValidation.isEmpty()) {
            errors.put("weightGram", weightGramValidation);
        }

        String priceValidation = Validator.isPriceValid(this.price, true);
        if (!priceValidation.isEmpty()) {
            errors.put("price", priceValidation);
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Invalid request", errors);
        }
    }
}
