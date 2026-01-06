package com.logistics.company.controllers;

import com.logistics.company.dtos.shipment.CreateShipmentRequestDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.services.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shipment")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping()
    public ResponseEntity<ShipmentDTO> createShipment(@RequestBody CreateShipmentRequestDTO dto) {
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.shipmentService.createShipment(dto));
    }
}
