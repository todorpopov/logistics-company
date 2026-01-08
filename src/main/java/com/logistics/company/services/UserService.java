package com.logistics.company.services;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.courier_employee.CourierEmployeeDTO;
import com.logistics.company.dtos.office_employee.OfficeEmployeeDTO;
import com.logistics.company.dtos.office_employee.UpdateOfficeEmployeeRequestDTO;
import com.logistics.company.dtos.user.CreateUserRequestDTO;
import com.logistics.company.dtos.user.UpdateUserRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.*;
import com.logistics.company.models.enums.UserRole;
import com.logistics.company.repositories.*;
import com.logistics.company.util.DtoMapper;
import com.logistics.company.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    private final ClientRepository clientRepository;
    private final OfficeEmployeeRepository officeEmployeeRepository;
    private final CourierEmployeeRepository courierEmployeeRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    public UserService(
        ClientRepository clientRepository,
        OfficeEmployeeRepository officeEmployeeRepository,
        CourierEmployeeRepository courierEmployeeRepository,
        OfficeRepository officeRepository,
        UserRepository userRepository

    ) {
        this.clientRepository = clientRepository;
        this.officeEmployeeRepository = officeEmployeeRepository;
        this.courierEmployeeRepository = courierEmployeeRepository;
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
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
    public ClientDTO updateClient(Long clientId, UpdateUserRequestDTO updateUserRequestDTO) {
        if (updateUserRequestDTO.isInvalid() || !Validator.isIdValid(clientId, true)) {
            throw new BadRequestException("Invalid request");
        }

        try {
            Client client = this.clientRepository.findById(clientId).orElseThrow(
                () -> new BadRequestException("Client not found")
            );
            User user = client.getUser();

            user.setFirstName(updateUserRequestDTO.getFirstName());
            user.setLastName(updateUserRequestDTO.getLastName());
            user.setEmail(updateUserRequestDTO.getEmail());

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

    @Transactional
    public CourierEmployeeDTO updateCourierEmployee(Long courierEmployeeId, UpdateUserRequestDTO updateUserRequestDTO) {
        if (updateUserRequestDTO.isInvalid() || !Validator.isIdValid(courierEmployeeId, true)) {
            throw new BadRequestException("Invalid request");
        }

        try {
            CourierEmployee courierEmployee = this.courierEmployeeRepository.findById(courierEmployeeId).orElseThrow(
                () -> new BadRequestException("Courier employee not found")
            );
            User user = courierEmployee.getUser();

            user.setFirstName(updateUserRequestDTO.getFirstName());
            user.setLastName(updateUserRequestDTO.getLastName());
            user.setEmail(updateUserRequestDTO.getEmail());

            CourierEmployee updatedCourierEmployee = this.courierEmployeeRepository.save(courierEmployee);
            return DtoMapper.courierEmployeeEntityToDto(updatedCourierEmployee);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Courier employee not found");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public OfficeEmployeeDTO updateOfficeEmployee(
        Long officeEmployeeId,
        UpdateOfficeEmployeeRequestDTO updateOfficeEmployeeRequestDTO
    ) {
        if (updateOfficeEmployeeRequestDTO.isInvalid() || !Validator.isIdValid(officeEmployeeId, true)) {
            throw new BadRequestException("Invalid request");
        }

        try {
            OfficeEmployee officeEmployee = this.officeEmployeeRepository.findById(officeEmployeeId).orElseThrow(
                () -> new BadRequestException("Courier employee not found")
            );
            User user = officeEmployee.getUser();

            user.setFirstName(updateOfficeEmployeeRequestDTO.getFirstName());
            user.setLastName(updateOfficeEmployeeRequestDTO.getLastName());
            user.setEmail(updateOfficeEmployeeRequestDTO.getEmail());

            if (!Objects.equals(updateOfficeEmployeeRequestDTO.getOfficeId(), officeEmployee.getOffice().getOfficeId())) {
                Office office = this.officeRepository.findById(updateOfficeEmployeeRequestDTO.getOfficeId()).orElseThrow(
                    () -> new BadRequestException("Office not found")
                );
                officeEmployee.setOffice(office);
            }

            OfficeEmployee updatedOfficeEmployee = this.officeEmployeeRepository.save(officeEmployee);
            return DtoMapper.officeEmployeeEntityToDto(updatedOfficeEmployee);
        } catch (BadRequestException | DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Courier employee not found");
        }
    }

    @Transactional
    public void deleteUser(Long entityId, UserRole userRole) {
        if (!Validator.isIdValid(entityId, true)) {
            throw new BadRequestException("Invalid request");
        }

        try {
            switch (userRole) {
                case CLIENT -> this.clientRepository.deleteById(entityId);
                case OFFICE_EMPLOYEE -> this.officeEmployeeRepository.deleteById(entityId);
                case COURIER_EMPLOYEE -> this.courierEmployeeRepository.deleteById(entityId);
            }

        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("User not found");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
