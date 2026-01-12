package com.logistics.company.controllers;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.user.EmployeeDTO;
import com.logistics.company.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("employees")
    public ResponseEntity<Iterable<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(this.reportService.getAllEmployees());
    }

    @GetMapping("clients")
    public ResponseEntity<Iterable<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(this.reportService.getAllClients());
    }
}
