package com.logistics.company.controllers;

import com.logistics.company.dtos.shipment.CreateShipmentRequestDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.dtos.shipment.UpdateShipmentRequestDTO;
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
        dto.validate();
        return ResponseEntity.ok(this.shipmentService.createShipment(dto));
    }

    @GetMapping()
    public ResponseEntity<Iterable<ShipmentDTO>> getAllShipments(){
        return ResponseEntity.ok(this.shipmentService.getAllShipments());
    }

    @PutMapping("{shipmentId}")
    public ResponseEntity<ShipmentDTO> updateShipment(@PathVariable Long shipmentId, @RequestBody UpdateShipmentRequestDTO dto) {
        try {
            dto.validate();
        } catch (BadRequestException e) {
            String shipmentIdValidation = Validator.isIdValidMsg(shipmentId, true);
            if (!shipmentIdValidation.isEmpty()) {
                e.setError("shipmentId", "Invalid shipment id");
            }
            throw e;
        }
        return ResponseEntity.ok(this.shipmentService.updateShipment(shipmentId, dto));
    }

    @DeleteMapping("{shipmentId}")
    public void deleteShipment(@PathVariable Long shipmentId){
        if(Validator.isIdInvalid(shipmentId, true)){
            throw new BadRequestException("Invalid shipment id");
        }
        this.shipmentService.deleteShipment(shipmentId);
    }
}
