package com.logistics.company.services;

import com.logistics.company.dtos.shipment.CreateShipmentRequestDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.*;
import com.logistics.company.models.enums.ShipmentStatus;
import com.logistics.company.repositories.*;
import com.logistics.company.util.DtoMapper;
import com.logistics.company.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShipmentService {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class.getName());

    private final ShipmentRepository shipmentRepository;
    private final OfficeRepository officeRepository;
    private final ClientRepository clientRepository;
    private final OfficeEmployeeRepository officeEmployeeRepository;
    private final CourierEmployeeRepository courierEmployeeRepository;

    public ShipmentService(
        ShipmentRepository shipmentRepository,
        OfficeRepository officeRepository,
        ClientRepository clientRepository,
        OfficeEmployeeRepository officeEmployeeRepository,
        CourierEmployeeRepository courierEmployeeRepository
    ) {
        this.shipmentRepository = shipmentRepository;
        this.officeRepository = officeRepository;
        this.clientRepository = clientRepository;
        this.officeEmployeeRepository = officeEmployeeRepository;
        this.courierEmployeeRepository = courierEmployeeRepository;
    }

    @Transactional
    public ShipmentDTO createShipment(CreateShipmentRequestDTO createShipmentRequestDTO) {
        if (createShipmentRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        LocalDate today = LocalDate.now();
        ShipmentStatus status = ShipmentStatus.PENDING;
        try {
            Client sender = this.clientRepository.findById(createShipmentRequestDTO.getSenderId())
                .orElseThrow(() -> new BadRequestException("Sender not found"));
            OfficeEmployee registeredBy = this.officeEmployeeRepository.findById(createShipmentRequestDTO.getRegisteredById())
                .orElseThrow(() -> new BadRequestException("Office employee not found"));
            Office deliveryOffice = this.officeRepository.findById(createShipmentRequestDTO.getDeliveryOfficeId())
                .orElseThrow(() -> new BadRequestException("Office not found"));
            CourierEmployee courierEmployee = this.courierEmployeeRepository.findById(createShipmentRequestDTO.getCourierEmployeeId())
                .orElseThrow(() -> new BadRequestException("Courier employee not found"));

            Shipment shipment = Shipment.builder()
                .sender(sender)
                .registeredBy(registeredBy)
                .deliveryOffice(deliveryOffice)
                .courierEmployee(courierEmployee)
                .price(createShipmentRequestDTO.getPrice())
                .weightGram(createShipmentRequestDTO.getWeightGram())
                .phoneNumber(createShipmentRequestDTO.getPhoneNumber())
                .status(status)
                .deliveryType(createShipmentRequestDTO.getDeliveryType())
                .sentDate(today)
                .deliveredDate(null)
                .build();

            this.shipmentRepository.save(shipment);
            return DtoMapper.shipmentEntityToDto(shipment);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Cannot create shipment");
        }
    }

    public List<ShipmentDTO> getAllShipments() {
        try {
            List<Shipment> shipments = this.shipmentRepository.findAll();
            return shipments.stream()
                .map(DtoMapper::shipmentEntityToDto)
                .toList();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Cannot create shipment");
        }
    }

    public void deleteShipment(Long shipmentId) {
        if (!Validator.isIdValid(shipmentId, true)) {
            throw new BadRequestException("Invalid request");
        }

        if (!this.shipmentRepository.existsById(shipmentId)) {
            throw new BadRequestException("Shipment not found");
        }

        try {
            this.shipmentRepository.deleteById(shipmentId);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Cannot delete shipment");
        }
    }
}
