package com.logistics.company.controllers;

import com.logistics.company.dtos.shipment.CreateShipmentRequestDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.services.ShipmentService;
import com.logistics.company.util.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("all")
    public ResponseEntity<Iterable<ShipmentDTO>> getAllShipments(){
        return ResponseEntity.ok(this.shipmentService.getAllShipments());
    }

    @DeleteMapping("{shipmentId}")
    public void deleteShipment(@PathVariable Long shipmentId){
        if(!Validator.isIdValid(shipmentId, true)){
            throw new BadRequestException("Invalid request");
        }
        this.shipmentService.deleteShipment(shipmentId);
    }
}
