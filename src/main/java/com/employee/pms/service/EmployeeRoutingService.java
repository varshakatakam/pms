package com.employee.pms.service;

import com.employee.pms.repository.PostgresEmployeeRepository;
import com.employee.pms.repository.H2EmployeeRepository;
import org.springframework.stereotype.Service;
import com.employee.pms.entity.Employee;
import com.employee.pms.entity.EmployeeType;
import java.util.List;

@Service
public class EmployeeRoutingService {

    private final PostgresEmployeeRepository postgresRepository;
    private final H2EmployeeRepository h2Repository;

    public EmployeeRoutingService(
            PostgresEmployeeRepository postgresRepository,
            H2EmployeeRepository h2Repository) {

        this.postgresRepository = postgresRepository;
        this.h2Repository = h2Repository;
    }
    public Employee createEmployee(Employee employee) {

        if (employee.getEmployeeType() == null) {
            throw new IllegalArgumentException(
                    "Employee type is required"
            );
        }

        if (employee.getEmployeeType() == EmployeeType.PERMANENT) {

            return postgresRepository.saveEmployee(employee);

        } else if (employee.getEmployeeType() == EmployeeType.CONTRACTOR) {

            return h2Repository.saveEmployee(employee);

        } else {

            throw new IllegalArgumentException(
                    "Invalid employee type"
            );
        }
    }
    public Employee getEmployeeById(Long id, EmployeeType employeeType) {

        if (employeeType == null) {
            throw new IllegalArgumentException(
                    "Employee type is required"
            );
        }

        if (employeeType == EmployeeType.PERMANENT) {

            return postgresRepository.getEmployeeById(id);

        } else if (employeeType == EmployeeType.CONTRACTOR) {

            return h2Repository.getEmployeeById(id);

        } else {

            throw new IllegalArgumentException(
                    "Invalid employee type"
            );
        }
    }
    public List<Employee> getAllEmployees(EmployeeType employeeType) {

        if (employeeType == null) {
            throw new IllegalArgumentException(
                    "Employee type is required"
            );
        }

        if (employeeType == EmployeeType.PERMANENT) {

            return postgresRepository.getAllEmployees();

        } else if (employeeType == EmployeeType.CONTRACTOR) {

            return h2Repository.getAllEmployees();

        } else {

            throw new IllegalArgumentException(
                    "Invalid employee type"
            );
        }
    }
    public Employee updateEmployee(
            Long id,
            EmployeeType employeeType,
            Employee updatedEmployee) {

        if (employeeType == null) {
            throw new IllegalArgumentException(
                    "Employee type is required"
            );
        }

        if (updatedEmployee.getEmployeeType() != null
                && updatedEmployee.getEmployeeType() != employeeType) {
            throw new IllegalArgumentException(
                    "Employee type cannot be changed"
            );
        }

        if (employeeType == EmployeeType.PERMANENT) {

            return postgresRepository.updateEmployee(id, updatedEmployee);

        } else if (employeeType == EmployeeType.CONTRACTOR) {

            return h2Repository.updateEmployee(id, updatedEmployee);

        } else {

            throw new IllegalArgumentException(
                    "Invalid employee type"
            );
        }
    }
    public void deleteEmployee(Long id, EmployeeType employeeType) {

        if (employeeType == null) {
            throw new IllegalArgumentException(
                    "Employee type is required"
            );
        }

        if (employeeType == EmployeeType.PERMANENT) {

            postgresRepository.deleteEmployee(id);

        } else if (employeeType == EmployeeType.CONTRACTOR) {

            h2Repository.deleteEmployee(id);

        } else {

            throw new IllegalArgumentException(
                    "Invalid employee type"
            );
        }
    }
}