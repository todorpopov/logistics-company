package com.logistics.company.dtos.shipment;

import com.logistics.company.dtos.Validatable;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.enums.ShipmentDeliveryType;
import com.logistics.company.models.enums.ShipmentStatus;
import com.logistics.company.util.Validator;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShipmentRequestDTO implements Validatable {
    private Long deliveryOfficeId;
    private Long courierEmployeeId;
    private ShipmentDeliveryType deliveryType;
    private String phoneNumber;
    private BigDecimal price;
    private ShipmentStatus status;
    private LocalDate deliveredDate;

    public void validate() throws BadRequestException {
        HashMap<String, String> errors = new HashMap<>();

        String deliveryTypeValidation = Validator.isDeliveryTypeValid(this.deliveryType, this.deliveryOfficeId, this.courierEmployeeId);
        if (!deliveryTypeValidation.isEmpty()) {
            errors.put("deliveryType", deliveryTypeValidation);
        }

        String deliveryOfficeIdValidation = Validator.isIdValidMsg(this.deliveryOfficeId, false);
        if (!deliveryOfficeIdValidation.isEmpty()) {
            errors.put("deliveryOfficeId", deliveryOfficeIdValidation);
        }

        String courierEmployeeIdValidation = Validator.isIdValidMsg(this.courierEmployeeId, false);
        if (!courierEmployeeIdValidation.isEmpty()) {
            errors.put("courierEmployeeId", courierEmployeeIdValidation);
        }

        String phoneNumberValidation = Validator.isPhoneNumberValid(this.phoneNumber, true);
        if (!phoneNumberValidation.isEmpty()) {
            errors.put("phoneNumber", phoneNumberValidation);
        }

        String priceValidation = Validator.isPriceValid(this.price, true);
        if (!priceValidation.isEmpty()) {
            errors.put("price", priceValidation);
        }

        if (this.status == null) {
            errors.put("status", "Status cannot be empty");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Invalid request", errors);
        }

        updateDeliveredShipment();
    }

    public void updateDeliveredShipment() {
        if (this.status == ShipmentStatus.DELIVERED && this.deliveredDate == null) {
            this.deliveredDate = LocalDate.now();
        }
        if (this.status != ShipmentStatus.DELIVERED && this.deliveredDate != null) {
            this.status = ShipmentStatus.DELIVERED;
        }
    }
}
