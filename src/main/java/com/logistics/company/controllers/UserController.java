package com.logistics.company.controllers;

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

    @GetMapping("client")
    public ResponseEntity<Iterable<ClientDTO>> getAllClients(){
        return ResponseEntity.ok(this.userService.getAllClients());
    }

    @GetMapping("office-employee")
    public ResponseEntity<Iterable<OfficeEmployeeDTO>> getAllOfficeEmployees(){
        return ResponseEntity.ok(this.userService.getAllOfficeEmployees());
    }

    @GetMapping("courier-employee")
    public ResponseEntity<Iterable<CourierEmployeeDTO>> getAllCourierEmployees(){
        return ResponseEntity.ok(this.userService.getAllCourierEmployees());
    }

    @PostMapping("office-employee")
    public ResponseEntity<OfficeEmployeeDTO> createOfficeEmployee(@RequestBody CreateUserRequestDTO dto) {
        dto.setOfficeEmployee(true);
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.userService.createOfficeEmployee(dto));
    }

    @PostMapping("courier-employee")
    public ResponseEntity<CourierEmployeeDTO> createCourierEmployee(@RequestBody CreateUserRequestDTO dto) {
        dto.setOfficeEmployee(false);
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.userService.createCourierEmployee(dto));
    }

    @PutMapping("client/{clientId}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long clientId, @RequestBody UpdateUserRequestDTO dto) {
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.userService.updateClient(clientId, dto));
    }

    @PutMapping("courier-employee/{courierEmployeeId}")
    public ResponseEntity<CourierEmployeeDTO> updateCourierEmployee(
        @PathVariable Long courierEmployeeId,
        @RequestBody UpdateUserRequestDTO dto
    ) {
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.userService.updateCourierEmployee(courierEmployeeId, dto));
    }

    @PutMapping("office-employee/{officeEmployeeId}")
    public ResponseEntity<OfficeEmployeeDTO> updateOfficeEmployee(
        @PathVariable Long officeEmployeeId,
        @RequestBody UpdateOfficeEmployeeRequestDTO dto
    ) {
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.userService.updateOfficeEmployee(officeEmployeeId, dto));
    }

    @DeleteMapping("client/{clientId}")
    public void deleteClient(@PathVariable Long clientId){
        if(!Validator.isIdValid(clientId, true)){
            throw new BadRequestException("Invalid request");
        }
        this.userService.deleteUser(clientId, UserRole.CLIENT);
    }

    @DeleteMapping("courier-employee/{courierEmployeeId}")
    public void deleteCourierEmployee(@PathVariable Long courierEmployeeId){
        if(!Validator.isIdValid(courierEmployeeId, true)){
            throw new BadRequestException("Invalid request");
        }
        this.userService.deleteUser(courierEmployeeId, UserRole.COURIER_EMPLOYEE);
    }

    @DeleteMapping("office-employee/{officeEmployeeId}")
    public void deleteOfficeEmployee(@PathVariable Long officeEmployeeId){
        if(!Validator.isIdValid(officeEmployeeId, true)){
            throw new BadRequestException("Invalid request");
        }
        this.userService.deleteUser(officeEmployeeId, UserRole.OFFICE_EMPLOYEE);
    }
}
