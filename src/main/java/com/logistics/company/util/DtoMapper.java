package com.logistics.company.util;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.courier_employee.CourierEmployeeDTO;
import com.logistics.company.dtos.office.OfficeDTO;
import com.logistics.company.dtos.office_employee.OfficeEmployeeDTO;
import com.logistics.company.dtos.shipment.ShipmentDTO;
import com.logistics.company.models.Client;
import com.logistics.company.models.CourierEmployee;
import com.logistics.company.models.OfficeEmployee;
import com.logistics.company.models.Shipment;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public static ClientDTO clientEntityToDto(Client client) {
        if (client == null) {
            return null;
        }
        return ClientDTO.builder()
            .clientId(client.getClientId())
            .userId(client.getUser().getUserId())
            .firstName(client.getUser().getFirstName())
            .lastName(client.getUser().getLastName())
            .email(client.getUser().getEmail())
            .build();
    }

    public static OfficeEmployeeDTO officeEmployeeEntityToDto(OfficeEmployee officeEmployee) {
        if (officeEmployee == null) {
            return null;
        }
        OfficeDTO officeDto = officeEmployee.getOffice() != null ? map(officeEmployee.getOffice(), OfficeDTO.class) : null;
        return OfficeEmployeeDTO.builder()
            .userId(officeEmployee.getUser().getUserId())
            .officeEmployeeId(officeEmployee.getOfficeEmployeeId())
            .office(officeDto)
            .email(officeEmployee.getUser().getEmail())
            .firstName(officeEmployee.getUser().getFirstName())
            .lastName(officeEmployee.getUser().getLastName())
            .build();
    }

    public static CourierEmployeeDTO courierEmployeeEntityToDto(CourierEmployee courierEmployee) {
        if (courierEmployee == null) {
            return null;
        }
        return CourierEmployeeDTO.builder()
            .userId(courierEmployee.getUser().getUserId())
            .courierEmployeeId(courierEmployee.getCourierEmployeeId())
            .email(courierEmployee.getUser().getEmail())
            .firstName(courierEmployee.getUser().getFirstName())
            .lastName(courierEmployee.getUser().getLastName())
            .build();
    }

    public static ShipmentDTO shipmentEntityToDto(Shipment shipment) {
        if (shipment == null) {
            return null;
        }
        OfficeDTO officeDto = shipment.getDeliveryOffice() != null ? map(shipment.getDeliveryOffice(), OfficeDTO.class) : null;
        return ShipmentDTO.builder()
            .shipmentId(shipment.getShipmentId())
            .sender(clientEntityToDto(shipment.getSender()))
            .registeredBy(officeEmployeeEntityToDto(shipment.getRegisteredBy()))
            .deliveryOffice(officeDto)
            .courierEmployee(courierEmployeeEntityToDto(shipment.getCourierEmployee()))
            .price(shipment.getPrice())
            .weightGram(shipment.getWeightGram())
            .phoneNumber(shipment.getPhoneNumber())
            .status(shipment.getStatus())
            .deliveryType(shipment.getDeliveryType())
            .sentDate(shipment.getSentDate())
            .deliveredDate(shipment.getDeliveredDate())
            .build();
    }
}
