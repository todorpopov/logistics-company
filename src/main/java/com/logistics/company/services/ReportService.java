package com.logistics.company.services;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.dtos.user.EmployeeDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class.getName());
    private final UserService userService;
    private final ShipmentService shipmentService;

    public ReportService(UserService userService, ShipmentService shipmentService) {
        this.userService = userService;
        this.shipmentService = shipmentService;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return this.userService.getAllEmployees();
    }

    public List<ClientDTO> getAllClients() {
        return this.userService.getAllClients();
    }

    public List<ShipmentDTO> getAllRegisteredShipments() {
        return this.shipmentService.getAllRegisteredShipments();
    }

    public List<ShipmentDTO> getAllShipmentsSentForDelivery() {
        return this.shipmentService.getAllShipmentsSentForDelivery();
    }

    public List<ShipmentDTO> getAllShipmentsRegisteredBy(Long officeEmployeeId) {
        if (Validator.isIdInvalid(officeEmployeeId, true)) {
            throw new BadRequestException("Invalid office employee id");
        }
        return this.shipmentService.getAllShipmentsRegisteredBy(officeEmployeeId);
    }

    public List<ShipmentDTO> getAllShipmentsSentByClient(Long clientId) {
        if (Validator.isIdInvalid(clientId, true)) {
            throw new BadRequestException("Invalid client id");
        }
        return this.shipmentService.getAllShipmentsSentByClient(clientId);
    }

    public List<ShipmentDTO> getAllShipmentsReceivedByClient(Long clientId) {
        if (Validator.isIdInvalid(clientId, true)) {
            throw new BadRequestException("Invalid client id");
        }
        return this.shipmentService.getAllShipmentsReceivedByClient(clientId);
    }

    public BigDecimal getTotalPriceOfShipmentsBetween(LocalDate startDate, LocalDate endDate) {
        return this.shipmentService.getTotalPriceOfShipmentsBetween(startDate, endDate);
    }
}
