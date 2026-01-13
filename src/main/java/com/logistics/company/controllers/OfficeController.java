package com.logistics.company.controllers;

import com.logistics.company.dtos.office.CreateUpdateOfficeRequestDTO;
import com.logistics.company.dtos.office.OfficeDTO;
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
    public ResponseEntity<OfficeDTO> createOffice(@RequestBody CreateUpdateOfficeRequestDTO dto) {
        dto.validate();
        return ResponseEntity.ok(this.officeService.createOffice(dto));
    }

    @GetMapping()
    public ResponseEntity<Iterable<OfficeDTO>> getAllOffices(){
        return ResponseEntity.ok(this.officeService.getAllOffices());
    }

    @PutMapping("{officeId}")
    public ResponseEntity<OfficeDTO> updateOffice(
        @PathVariable Long officeId,
        @RequestBody CreateUpdateOfficeRequestDTO dto
    ){
        try {
            dto.validate();
        } catch (BadRequestException e) {
            String officeIdValidation = Validator.isIdValidMsg(officeId, true);
            if (!officeIdValidation.isEmpty()) {
                e.setError("officeId", "Invalid office id");
            }
            throw e;
        }
        return ResponseEntity.ok(this.officeService.updateOffice(officeId, dto));
    }

    @DeleteMapping("{officeId}")
    public void deleteOffice(@PathVariable Long officeId){
        if(Validator.isIdInvalid(officeId, true)){
            throw new BadRequestException("Invalid office id");
        }
        this.officeService.deleteOffice(officeId);
    }
}
