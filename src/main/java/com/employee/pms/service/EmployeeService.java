package com.employee.pms.service;

import com.employee.pms.exception.EmployeeNotFoundException;
import com.employee.pms.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import com.employee.pms.entity.Employee;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }
    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException(id));
    }
    public Employee updateEmployee(Long id, Employee employee){
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        return employeeRepository.save(existingEmployee);
    }
    public void deleteEmployee(Long id){
        Employee existingEmployee = getEmployeeById(id);
        employeeRepository.deleteById(existingEmployee.getId());
    }
}
