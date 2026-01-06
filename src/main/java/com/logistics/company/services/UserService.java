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

    @Transactional
    public CourierEmployeeDTO updateCourierEmployee(UpdateUserRequestDTO updateUserRequestDTO) {
        if (updateUserRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        try {
            CourierEmployee courierEmployee = null;
            if (updateUserRequestDTO.getEntityId() != null) {
                courierEmployee = this.courierEmployeeRepository.findById(updateUserRequestDTO.getEntityId()).orElseThrow();
            } else if (updateUserRequestDTO.getUserId() != null) {
                courierEmployee = this.courierEmployeeRepository.findByUser_UserId(updateUserRequestDTO.getUserId()).orElseThrow();
            }
            if (courierEmployee == null) {
                throw new BadRequestException("Courier employee not found");
            }

            User user = courierEmployee.getUser();

            if (updateUserRequestDTO.getFirstName() != null) {
                user.setFirstName(updateUserRequestDTO.getFirstName());
            }
            if (updateUserRequestDTO.getLastName() != null) {
                user.setLastName(updateUserRequestDTO.getLastName());
            }
            if (updateUserRequestDTO.getEmail() != null) {
                user.setEmail(updateUserRequestDTO.getEmail());
            }

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
    public OfficeEmployeeDTO updateOfficeEmployee(UpdateOfficeEmployeeRequestDTO updateOfficeEmployeeRequestDTO) {
        if (updateOfficeEmployeeRequestDTO.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }

        try {
            OfficeEmployee officeEmployee = null;
            if (updateOfficeEmployeeRequestDTO.getEntityId() != null) {
                officeEmployee = this.officeEmployeeRepository.findById(updateOfficeEmployeeRequestDTO.getEntityId()).orElseThrow();
            } else if (updateOfficeEmployeeRequestDTO.getUserId() != null) {
                officeEmployee = this.officeEmployeeRepository.findByUser_UserId(updateOfficeEmployeeRequestDTO.getUserId()).orElseThrow();
            }
            if (officeEmployee == null) {
                throw new BadRequestException("Office employee not found");
            }

            User user = officeEmployee.getUser();

            if (updateOfficeEmployeeRequestDTO.getFirstName() != null) {
                user.setFirstName(updateOfficeEmployeeRequestDTO.getFirstName());
            }
            if (updateOfficeEmployeeRequestDTO.getLastName() != null) {
                user.setLastName(updateOfficeEmployeeRequestDTO.getLastName());
            }
            if (updateOfficeEmployeeRequestDTO.getEmail() != null) {
                user.setEmail(updateOfficeEmployeeRequestDTO.getEmail());
            }
            if (updateOfficeEmployeeRequestDTO.getOfficeId() != null) {
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
    public void deleteUser(Long userId) {
        if (!Validator.isIdValid(userId, true)) {
            throw new BadRequestException("Invalid request");
        }

        try {
            User user = this.userRepository.findById(userId).orElseThrow();

            switch (user.getUserRole()) {
                case CLIENT -> this.clientRepository.deleteByUser_UserId(userId);
                case OFFICE_EMPLOYEE -> this.officeEmployeeRepository.deleteByUser_UserId(userId);
                case COURIER_EMPLOYEE -> this.courierEmployeeRepository.deleteByUser_UserId(userId);
            }

            this.userRepository.delete(user);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("User not found");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
