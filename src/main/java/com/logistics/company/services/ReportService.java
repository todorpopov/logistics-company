package com.logistics.company.services;

import com.logistics.company.dtos.client.ClientDTO;
import com.logistics.company.dtos.user.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class.getName());
    private final UserService userService;

    public ReportService(UserService userService) {
        this.userService = userService;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return this.userService.getAllEmployees();
    }

    public List<ClientDTO> getAllClients() {
        return this.userService.getAllClients();
    }
}
