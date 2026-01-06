package com.logistics.company.services;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.courier_employee.CourierEmployeeDTO;
import com.logistics.company.dtos.office_employee.OfficeEmployeeDTO;
import com.logistics.company.dtos.user.CreateUserRequestDTO;
import com.logistics.company.dtos.user.UpdateUserRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.*;
import com.logistics.company.models.enums.UserRole;
import com.logistics.company.repositories.ClientRepository;
import com.logistics.company.repositories.CourierEmployeeRepository;
import com.logistics.company.repositories.OfficeEmployeeRepository;
import com.logistics.company.repositories.OfficeRepository;
import com.logistics.company.util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    private final ClientRepository clientRepository;
    private final OfficeEmployeeRepository officeEmployeeRepository;
    private final CourierEmployeeRepository courierEmployeeRepository;
    private final OfficeRepository officeRepository;

    public UserService(
        ClientRepository clientRepository,
        OfficeEmployeeRepository officeEmployeeRepository,
        CourierEmployeeRepository courierEmployeeRepository,
        OfficeRepository officeRepository

    ) {
        this.clientRepository = clientRepository;
        this.officeEmployeeRepository = officeEmployeeRepository;
        this.courierEmployeeRepository = courierEmployeeRepository;
        this.officeRepository = officeRepository;
    }

    public List<ClientDTO> getAllClients() {
        try {
            List<Client> clients = this.clientRepository.findAll();
            return clients.stream()
                .map(DtoMapper::clientEntityToDto)
                .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public List<OfficeEmployeeDTO> getAllOfficeEmployees() {
        try {
            List<OfficeEmployee> officeEmployees = this.officeEmployeeRepository.findAll();
            return officeEmployees.stream()
                .map(DtoMapper::officeEmployeeEntityToDto)
                .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public List<CourierEmployeeDTO> getAllCourierEmployees() {
        try {
            List<CourierEmployee> courierEmployees = this.courierEmployeeRepository.findAll();
            return courierEmployees.stream()
                .map(DtoMapper::courierEmployeeEntityToDto)
                .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public OfficeEmployeeDTO createOfficeEmployee(CreateUserRequestDTO createUserRequestDTO) {
        createUserRequestDTO.setOfficeEmployee(true);
        if (createUserRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        User user = User.builder()
            .firstName(createUserRequestDTO.getFirstName())
            .lastName(createUserRequestDTO.getLastName())
            .email(createUserRequestDTO.getEmail())
            .passwordHash(BcryptUtils.hashPassword(createUserRequestDTO.getPassword()))
            .userRole(UserRole.OFFICE_EMPLOYEE)
            .build();

        try {
            Office office = this.officeRepository.findById(createUserRequestDTO.getOfficeId()).orElseThrow();
            OfficeEmployee officeEmployee = OfficeEmployee.builder()
                .user(user)
                .office(office)
                .build();
            this.officeEmployeeRepository.save(officeEmployee);
            return DtoMapper.officeEmployeeEntityToDto(officeEmployee);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Office not found");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public CourierEmployeeDTO createCourierEmployee(CreateUserRequestDTO createUserRequestDTO) {
        createUserRequestDTO.setOfficeEmployee(false);
        if (createUserRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        User user = User.builder()
            .firstName(createUserRequestDTO.getFirstName())
            .lastName(createUserRequestDTO.getLastName())
            .email(createUserRequestDTO.getEmail())
            .passwordHash(BcryptUtils.hashPassword(createUserRequestDTO.getPassword()))
            .userRole(UserRole.COURIER_EMPLOYEE)
            .build();

        CourierEmployee courierEmployee = CourierEmployee.builder()
            .user(user)
            .build();

        try {
            this.courierEmployeeRepository.save(courierEmployee);
            return DtoMapper.courierEmployeeEntityToDto(courierEmployee);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public ClientDTO updateClient(UpdateUserRequestDTO updateUserRequestDTO) {
        if (updateUserRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        try {
            Client client = null;
            if (updateUserRequestDTO.getEntityId() != null) {
                client = this.clientRepository.findById(updateUserRequestDTO.getEntityId()).orElseThrow();
            } else if (updateUserRequestDTO.getUserId() != null) {
                client = this.clientRepository.findByUser_UserId(updateUserRequestDTO.getUserId()).orElseThrow();
            }
            if (client == null) {
                throw new BadRequestException("Client not found");
            }
            User user = client.getUser();

            if (updateUserRequestDTO.getFirstName() != null) {
                user.setFirstName(updateUserRequestDTO.getFirstName());
            }
            if (updateUserRequestDTO.getLastName() != null) {
                user.setLastName(updateUserRequestDTO.getLastName());
            }
            if (updateUserRequestDTO.getEmail() != null) {
                user.setEmail(updateUserRequestDTO.getEmail());
            }

            Client updatedClient = this.clientRepository.save(client);
            return DtoMapper.clientEntityToDto(updatedClient);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Client not found");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
