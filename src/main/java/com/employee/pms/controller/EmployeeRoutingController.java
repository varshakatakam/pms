package com.employee.pms.controller;

import com.employee.pms.service.EmployeeRoutingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.employee.pms.entity.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.employee.pms.entity.EmployeeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRoutingController {

    private final EmployeeRoutingService employeeRoutingService;

    public EmployeeRoutingController(
            EmployeeRoutingService employeeRoutingService) {

        this.employeeRoutingService = employeeRoutingService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody Employee employee) {

        return employeeRoutingService.createEmployee(employee);
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(
            @PathVariable Long id,
            @RequestParam EmployeeType employeeType) {

        return employeeRoutingService.getEmployeeById(id, employeeType);
    }
    @GetMapping
    public List<Employee> getAllEmployees(
            @RequestParam EmployeeType employeeType) {

        return employeeRoutingService.getAllEmployees(employeeType);
    }
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestParam EmployeeType employeeType,
            @Valid @RequestBody Employee updatedEmployee) {

        return employeeRoutingService.updateEmployee(
                id, employeeType, updatedEmployee
        );
    }
}
