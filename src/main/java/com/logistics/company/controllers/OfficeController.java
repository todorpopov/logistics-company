package com.logistics.company.controllers;

import com.logistics.company.dtos.office.CreateOfficeRequestDTO;
import com.logistics.company.dtos.office.OfficeDTO;
import com.logistics.company.dtos.office.UpdateOfficeRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.services.OfficeService;
import com.logistics.company.util.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/office")
public class OfficeController {
    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping()
    public ResponseEntity<OfficeDTO> createOffice(@RequestBody CreateOfficeRequestDTO dto) {
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.officeService.create(dto));
    }

    @GetMapping("all")
    public ResponseEntity<Iterable<OfficeDTO>> getAllOffices(){
        return ResponseEntity.ok(this.officeService.getAllOffices());
    }

    @PutMapping
    public ResponseEntity<OfficeDTO> updateOffice(@RequestBody UpdateOfficeRequestDTO dto){
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.officeService.update(dto));
    }

    @DeleteMapping("{officeId}")
    public void deleteOffice(@PathVariable Long officeId){
        if(!Validator.isIdValid(officeId, true)){
            throw new BadRequestException("Invalid request");
        }
        this.officeService.delete(officeId);
    }
}
