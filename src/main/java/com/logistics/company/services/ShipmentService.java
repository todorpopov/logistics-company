package com.logistics.company.services;

import com.logistics.company.dtos.shipment.CreateShipmentRequestDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.dtos.shipment.UpdateShipmentRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.*;
import com.logistics.company.models.enums.ShipmentDeliveryType;
import com.logistics.company.models.enums.ShipmentStatus;
import com.logistics.company.repositories.*;
import com.logistics.company.util.DtoMapper;
import com.logistics.company.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public ShipmentDTO createShipment(CreateShipmentRequestDTO dto) {
        dto.validate();

        try {
            Client sender = this.clientRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new BadRequestException("Sender not found"));
            Client receiver = this.clientRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new BadRequestException("Receiver not found"));
            OfficeEmployee registeredBy = this.officeEmployeeRepository.findById(dto.getRegisteredById())
                .orElseThrow(() -> new BadRequestException("Office employee not found"));

            Shipment.ShipmentBuilder builder = Shipment.builder();
            builder.sender(sender);
            builder.receiver(receiver);
            builder.registeredBy(registeredBy);
            builder.price(dto.getPrice());
            builder.weightGram(dto.getWeightGram());
            builder.phoneNumber(dto.getClientPhoneNumber());
            builder.status(ShipmentStatus.REGISTERED);
            builder.deliveryType(dto.getDeliveryType());
            builder.sentDate(LocalDate.now());
            builder.deliveredDate(null);

            switch (dto.getDeliveryType()) {
                case ShipmentDeliveryType.ADDRESS -> {
                    CourierEmployee courierEmployee = this.courierEmployeeRepository.findById(
                        dto.getCourierEmployeeId()
                    ).orElseThrow(() -> new BadRequestException("Courier employee not found"));
                    builder.courierEmployee(courierEmployee);
                }
                case ShipmentDeliveryType.OFFICE -> {
                    Office deliveryOffice = this.officeRepository.findById(
                        dto.getDeliveryOfficeId()
                    ).orElseThrow(() -> new BadRequestException("Office not found"));
                    builder.deliveryOffice(deliveryOffice);
                }
            }

            Shipment shipment = builder.build();
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
    public ShipmentDTO updateShipment(Long shipmentId, UpdateShipmentRequestDTO dto) {
        try {
            dto.validate();
        } catch (BadRequestException e) {
            String shipmentIdValidation = Validator.isIdValidMsg(shipmentId, true);
            if (!shipmentIdValidation.isEmpty()) {
                e.setError("shipmentId", "Invalid shipment id");
            }
            throw e;
        }

        try {
            Shipment shipment = this.shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new BadRequestException("Shipment not found"));

            if (dto.getDeliveryOfficeId() == null) {
                shipment.setDeliveryOffice(null);
            } else {
                Office deliveryOffice = this.officeRepository.findById(dto.getDeliveryOfficeId())
                    .orElseThrow(() -> new BadRequestException("Office not found"));
                shipment.setDeliveryOffice(deliveryOffice);
            }

            if (dto.getCourierEmployeeId() == null) {
                shipment.setCourierEmployee(null);
            } else {
                CourierEmployee courierEmployee = this.courierEmployeeRepository.findById(dto.getCourierEmployeeId())
                    .orElseThrow(() -> new BadRequestException("Courier not found"));
                shipment.setCourierEmployee(courierEmployee);
            }

            shipment.setDeliveryType(dto.getDeliveryType());
            shipment.setPhoneNumber(dto.getPhoneNumber());
            shipment.setPrice(dto.getPrice());
            shipment.setStatus(dto.getStatus());
            shipment.setDeliveredDate(dto.getDeliveredDate());

            Shipment updatedShipment = this.shipmentRepository.save(shipment);
            return DtoMapper.shipmentEntityToDto(updatedShipment);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Cannot update shipment");
        }
    }

    public void deleteShipment(Long shipmentId) {
        if (Validator.isIdInvalid(shipmentId, true)) {
            throw new BadRequestException("Invalid shipment id");
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
        if (Validator.isIdInvalid(officeEmployeeId, true)) {
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

    public List<ShipmentDTO> getAllShipmentsSentByClient(Long clientId) {
        if (Validator.isIdInvalid(clientId, true)) {
            throw new BadRequestException("Invalid request");
        }

        try {
            List<Shipment> registeredShipments = this.shipmentRepository.findAllBySender_ClientId(clientId);
            return registeredShipments.stream()
                .map(DtoMapper::shipmentEntityToDto)
                .toList();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    private List<ShipmentDTO> getAllDeliveredShipmentsBetween(LocalDate startDate, LocalDate endDate) {
        try {
            List<Shipment> shipments = this.shipmentRepository.findAllByDeliveredDateBetween(startDate, endDate);
            return shipments.stream()
                .map(DtoMapper::shipmentEntityToDto)
                .toList();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public BigDecimal getTotalPriceOfShipmentsBetween(LocalDate startDate, LocalDate endDate) {
        return this.getAllDeliveredShipmentsBetween(startDate, endDate).stream()
            .map(ShipmentDTO::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
