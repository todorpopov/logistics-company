package com.logistics.company.controllers;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.courier_employee.CourierEmployeeDTO;
import com.logistics.company.dtos.office_employee.OfficeEmployeeDTO;
import com.logistics.company.dtos.user.CreateUserRequestDTO;
import com.logistics.company.dtos.user.UpdateUserRequestDTO;
import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("client/all")
    public ResponseEntity<Iterable<ClientDTO>> getAllClients(){
        return ResponseEntity.ok(this.userService.getAllClients());
    }

    @GetMapping("office-employee/all")
    public ResponseEntity<Iterable<OfficeEmployeeDTO>> getAllOfficeEmployees(){
        return ResponseEntity.ok(this.userService.getAllOfficeEmployees());
    }

    @GetMapping("courier-employee/all")
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

    @PutMapping("client")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody UpdateUserRequestDTO dto) {
        if (dto.isInvalid()) {
            throw new BadRequestException("Invalid request");
        }
        return ResponseEntity.ok(this.userService.updateClient(dto));
    }
}
