package com.logistics.company.dtos.shipment;

import com.logistics.company.dtos.Validatable;
import com.logistics.company.models.enums.ShipmentDeliveryType;
import com.logistics.company.models.enums.ShipmentStatus;
import com.logistics.company.util.Validator;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public boolean isInvalid() {
        return !Validator.isShipmentTypeValid(this.deliveryType, this.deliveryOfficeId, this.courierEmployeeId)
            || !Validator.isIdValid(this.deliveryOfficeId, false)
            || !Validator.isIdValid(this.courierEmployeeId, false)
            || deliveryType == null
            || !Validator.isPhoneNumberValid(this.phoneNumber, true)
            || !Validator.isPriceValid(this.price, true)
            || status == null;
    }
}
