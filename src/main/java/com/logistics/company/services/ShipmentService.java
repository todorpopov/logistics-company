package com.logistics.company.services;

import com.logistics.company.dtos.shipment.CreateShipmentRequestDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.dtos.shipment.UpdateShipmentRequestDTO;
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
        ShipmentStatus status = ShipmentStatus.REGISTERED;
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
                .phoneNumber(createShipmentRequestDTO.getClientPhoneNumber())
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

    @Transactional
    public ShipmentDTO updateShipment(Long shipmentId, UpdateShipmentRequestDTO updateShipmentRequestDTO) {
        if (updateShipmentRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        try {
            Shipment shipment = this.shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new BadRequestException("Shipment not found"));

            if (updateShipmentRequestDTO.getDeliveryOfficeId() == null) {
                shipment.setDeliveryOffice(null);
            } else {
                Office deliveryOffice = this.officeRepository.findById(updateShipmentRequestDTO.getDeliveryOfficeId())
                    .orElseThrow(() -> new BadRequestException("Office not found"));
                shipment.setDeliveryOffice(deliveryOffice);
            }

            if (updateShipmentRequestDTO.getCourierEmployeeId() == null) {
                shipment.setCourierEmployee(null);
            } else {
                CourierEmployee courierEmployee = this.courierEmployeeRepository.findById(updateShipmentRequestDTO.getCourierEmployeeId())
                    .orElseThrow(() -> new BadRequestException("Courier not found"));
                shipment.setCourierEmployee(courierEmployee);
            }

            shipment.setDeliveryType(updateShipmentRequestDTO.getDeliveryType());
            shipment.setPhoneNumber(updateShipmentRequestDTO.getPhoneNumber());
            shipment.setPrice(updateShipmentRequestDTO.getPrice());
            shipment.setStatus(updateShipmentRequestDTO.getStatus());
            shipment.setDeliveredDate(updateShipmentRequestDTO.getDeliveredDate());

            Shipment updatedShipment = this.shipmentRepository.save(shipment);
            return DtoMapper.shipmentEntityToDto(updatedShipment);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Cannot update shipment");
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

    private List<ShipmentDTO> getShipmentsByStatus(ShipmentStatus status) {
        try {
            List<Shipment> registeredShipments = this.shipmentRepository.findAllByStatusIs(status);
            return registeredShipments.stream()
                .map(DtoMapper::shipmentEntityToDto)
                .toList();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public List<ShipmentDTO> getAllRegisteredShipments() {
        return this.getShipmentsByStatus(ShipmentStatus.REGISTERED);
    }

    public List<ShipmentDTO> getAllShipmentsRegisteredBy(Long officeEmployeeId) {
        if (!Validator.isIdValid(officeEmployeeId, true)) {
            throw new BadRequestException("Invalid request");
        }

        try {
            List<Shipment> registeredShipments = this.shipmentRepository.findAllByRegisteredBy_OfficeEmployeeId(officeEmployeeId);
            return registeredShipments.stream()
                .map(DtoMapper::shipmentEntityToDto)
                .toList();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public List<ShipmentDTO> getAllShipmentsSentForDelivery() {
        return this.getShipmentsByStatus(ShipmentStatus.SENT_FOR_DELIVERY);
    }
}
