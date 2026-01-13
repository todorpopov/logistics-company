package com.logistics.company.services;

import com.logistics.company.dtos.office.CreateUpdateOfficeRequestDTO;
import com.logistics.company.dtos.office.OfficeDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.Office;
import com.logistics.company.repositories.OfficeRepository;
import com.logistics.company.util.DtoMapper;
import com.logistics.company.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OfficeService {
    private static final Logger logger = LoggerFactory.getLogger(OfficeService.class.getName());

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public OfficeDTO createOffice(CreateUpdateOfficeRequestDTO dto) throws RuntimeException {
        dto.validate();
        Office office = Office.builder()
            .name(dto.getName())
            .address(dto.getAddress())
            .phoneNumber(dto.getPhoneNumber())
            .build();
        try {
            Office savedOffice = this.officeRepository.save(office);
            return DtoMapper.map(savedOffice, OfficeDTO.class);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public List<OfficeDTO> getAllOffices() {
        try {
            List<Office> offices = this.officeRepository.findAll();
            return DtoMapper.mapList(offices, OfficeDTO.class);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public OfficeDTO updateOffice(Long officeId, CreateUpdateOfficeRequestDTO dto) {
        try {
            dto.validate();
        } catch (BadRequestException e) {
            String officeIdValidation = Validator.isIdValidMsg(officeId, true);
            if (!officeIdValidation.isEmpty()) {
                e.setError("officeId", "Invalid office id");
            }
            throw e;
        }

        try {
            Office office = this.officeRepository.findById(officeId).orElseThrow();

            office.setName(dto.getName());
            office.setAddress(dto.getAddress());
            office.setPhoneNumber(dto.getPhoneNumber());

            Office updatedOffice = this.officeRepository.save(office);
            return DtoMapper.map(updatedOffice, OfficeDTO.class);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Office not found");
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public void deleteOffice(Long officeId) {
        if (Validator.isIdInvalid(officeId, true)) {
            throw new BadRequestException("Invalid office id");
        }

        if (!officeRepository.existsById(officeId)) {
            throw new BadRequestException("Office not found");
        }

        try {
            this.officeRepository.deleteById(officeId);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new BadRequestException("Cannot delete office");
        }
    }
}
