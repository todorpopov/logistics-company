package com.logistics.company.controllers;

import com.logistics.company.annotations.AuthGuard;
import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.courier_employee.CourierEmployeeDTO;
import com.logistics.company.dtos.office_employee.OfficeEmployeeDTO;
import com.logistics.company.dtos.office_employee.UpdateOfficeEmployeeRequestDTO;
import com.logistics.company.dtos.user.CreateUserRequestDTO;
import com.logistics.company.dtos.user.UpdateUserRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.models.enums.UserRole;
import com.logistics.company.services.UserService;
import com.logistics.company.util.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @AuthGuard({ "ADMIN" })
    @GetMapping("client")
    public ResponseEntity<Iterable<ClientDTO>> getAllClients(){
        return ResponseEntity.ok(this.userService.getAllClients());
    }

    @AuthGuard({ "ADMIN" })
    @GetMapping("office-employee")
    public ResponseEntity<Iterable<OfficeEmployeeDTO>> getAllOfficeEmployees(){
        return ResponseEntity.ok(this.userService.getAllOfficeEmployees());
    }

    @AuthGuard({ "ADMIN" })
    @GetMapping("courier-employee")
    public ResponseEntity<Iterable<CourierEmployeeDTO>> getAllCourierEmployees(){
        return ResponseEntity.ok(this.userService.getAllCourierEmployees());
    }

    @AuthGuard({ "ADMIN" })
    @PostMapping("office-employee")
    public ResponseEntity<OfficeEmployeeDTO> createOfficeEmployee(@RequestBody CreateUserRequestDTO dto) {
        dto.validate();
        return ResponseEntity.ok(this.userService.createOfficeEmployee(dto));
    }

    @AuthGuard({ "ADMIN" })
    @PostMapping("courier-employee")
    public ResponseEntity<CourierEmployeeDTO> createCourierEmployee(@RequestBody CreateUserRequestDTO dto) {
        dto.validate();
        return ResponseEntity.ok(this.userService.createCourierEmployee(dto));
    }

    @AuthGuard({ "ADMIN" })
    @PutMapping("client/{clientId}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long clientId, @RequestBody UpdateUserRequestDTO dto) {
        try {
            dto.validate();
        } catch (BadRequestException e) {
            String clientIdValidation = Validator.isIdValidMsg(clientId, true);
            if (!clientIdValidation.isEmpty()) {
                e.setError("clientId", "Invalid client id");
            }
            throw e;
        }
        return ResponseEntity.ok(this.userService.updateClient(clientId, dto));
    }

    @AuthGuard({ "ADMIN" })
    @PutMapping("courier-employee/{courierEmployeeId}")
    public ResponseEntity<CourierEmployeeDTO> updateCourierEmployee(
        @PathVariable Long courierEmployeeId,
        @RequestBody UpdateUserRequestDTO dto
    ) {
        try {
            dto.validate();
        } catch (BadRequestException e) {
            String courierEmployeeIdValidation = Validator.isIdValidMsg(courierEmployeeId, true);
            if (!courierEmployeeIdValidation.isEmpty()) {
                e.setError("clientId", "Invalid courier employee id");
            }
            throw e;
        }
        return ResponseEntity.ok(this.userService.updateCourierEmployee(courierEmployeeId, dto));
    }

    @AuthGuard({ "ADMIN" })
    @PutMapping("office-employee/{officeEmployeeId}")
    public ResponseEntity<OfficeEmployeeDTO> updateOfficeEmployee(
        @PathVariable Long officeEmployeeId,
        @RequestBody UpdateOfficeEmployeeRequestDTO dto
    ) {
        try {
            dto.validate();
        } catch (BadRequestException e) {
            String officeEmployeeIdValidation = Validator.isIdValidMsg(officeEmployeeId, true);
            if (!officeEmployeeIdValidation.isEmpty()) {
                e.setError("officeEmployeeId", "Invalid office employee id");
            }
            throw e;
        }
        return ResponseEntity.ok(this.userService.updateOfficeEmployee(officeEmployeeId, dto));
    }

    @AuthGuard({ "ADMIN" })
    @DeleteMapping("client/{clientId}")
    public void deleteClient(@PathVariable Long clientId){
        if(Validator.isIdInvalid(clientId, true)){
            throw new BadRequestException("Invalid client id");
        }
        this.userService.deleteUser(clientId, UserRole.CLIENT);
    }

    @AuthGuard({ "ADMIN" })
    @DeleteMapping("courier-employee/{courierEmployeeId}")
    public void deleteCourierEmployee(@PathVariable Long courierEmployeeId){
        if(Validator.isIdInvalid(courierEmployeeId, true)){
            throw new BadRequestException("Invalid courier employee id");
        }
        this.userService.deleteUser(courierEmployeeId, UserRole.COURIER_EMPLOYEE);
    }

    @AuthGuard({ "ADMIN" })
    @DeleteMapping("office-employee/{officeEmployeeId}")
    public void deleteOfficeEmployee(@PathVariable Long officeEmployeeId){
        if(Validator.isIdInvalid(officeEmployeeId, true)){
            throw new BadRequestException("Invalid office employee id");
        }
        this.userService.deleteUser(officeEmployeeId, UserRole.OFFICE_EMPLOYEE);
    }
}
