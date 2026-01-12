package com.logistics.company.dtos.shipment;


import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.courier_employee.CourierEmployeeDTO;
import com.logistics.company.dtos.office.OfficeDTO;
import com.logistics.company.dtos.office_employee.OfficeEmployeeDTO;
import com.logistics.company.models.enums.ShipmentDeliveryType;
import com.logistics.company.models.enums.ShipmentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    private Long shipmentId;
    private ClientDTO sender;
    private OfficeEmployeeDTO registeredBy;
    private OfficeDTO deliveryOffice;
    private CourierEmployeeDTO courierEmployee;
    private ShipmentDeliveryType deliveryType;
    private String clientPhoneNumber;
    private Integer weightGram;
    private BigDecimal price;
    private ShipmentStatus status;
    private LocalDate sentDate;
    private LocalDate deliveredDate;
}
