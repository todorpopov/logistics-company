package com.logistics.company.controllers;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.dtos.user.EmployeeDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.services.ReportService;
import com.logistics.company.util.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("api/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("employees")
    public ResponseEntity<Iterable<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(this.reportService.getAllEmployees());
    }

    @GetMapping("clients")
    public ResponseEntity<Iterable<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(this.reportService.getAllClients());
    }

    @GetMapping("registered-shipments")
    public ResponseEntity<Iterable<ShipmentDTO>> getAllRegisteredShipments() {
        return ResponseEntity.ok(this.reportService.getAllRegisteredShipments());
    }

    @GetMapping("shipments-sent-for-delivery")
    public ResponseEntity<Iterable<ShipmentDTO>> getAllShipmentsSentForDelivery() {
        return ResponseEntity.ok(this.reportService.getAllShipmentsSentForDelivery());
    }

    @GetMapping("shipments-registered-by/{officeEmployeeId}")
    public ResponseEntity<Iterable<ShipmentDTO>> getAllShipmentsRegisteredBy(@PathVariable Long officeEmployeeId) {
        if (Validator.isIdInvalid(officeEmployeeId, true)) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.reportService.getAllShipmentsRegisteredBy(officeEmployeeId));
    }

    @GetMapping("shipments-sent-by/{clientId}")
    public ResponseEntity<Iterable<ShipmentDTO>> getAllShipmentsSentByClient(@PathVariable Long clientId) {
        if (Validator.isIdInvalid(clientId, true)) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.reportService.getAllShipmentsSentByClient(clientId));
    }

    @GetMapping("total-gross-income-for-period/{startDate}/{endDate}")
    public BigDecimal getTotalPriceOfShipmentsBetween(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return this.reportService.getTotalPriceOfShipmentsBetween(startDate, endDate);
    }
}
