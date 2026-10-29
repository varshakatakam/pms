package com.employee.pms.service;

import com.employee.pms.repository.EmployeeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import com.employee.pms.entity.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import com.employee.pms.exception.EmployeeNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testCreateEmployee(){
        //Arrange:Create an employee
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);

        //Mock the repository response
        when(employeeRepository.save(employee)).thenReturn(employee);

        //Act: Call the service method
        Employee savedEmployee = employeeService.createEmployee(employee);

        //Assert:Verify the Result
        assertEquals("Rahul", savedEmployee.getName());
        assertEquals("rahul@gmail.com", savedEmployee.getEmail());
        assertEquals("IT", savedEmployee.getDepartment());
        assertEquals(50000.0, savedEmployee.getSalary());
    }
    @Test
    void testGetAllEmployees(){

        //Arrange:Create the first employee
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Rahul");
        employee1.setEmail("rahul@gmail.com");
        employee1.setDepartment("IT");
        employee1.setSalary(50000.0);

        //Create the second employee
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Priya");
        employee2.setEmail("priya@gmail.com");
        employee2.setDepartment("HR");
        employee2.setSalary(60000.0);

        //Create a list of employees
        List<Employee> employees = Arrays.asList(employee1, employee2);

        //Mock the repository response
        when(employeeRepository.findAll()).thenReturn(employees);

        //Act: Call the service method
        List<Employee> result = employeeService.getAllEmployees();

        //Assert: Verify the results
        assertEquals(2, result.size());
        assertEquals("Rahul", result.get(0).getName());
        assertEquals("Priya", result.get(1).getName());
    }
    @Test
    void testGetEmployeeById(){

        //Arrange: Create an employee
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);

        //Mock the repository response
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        //Act: Retrieve the employee
        Employee result = employeeService.getEmployeeById(1L);

        //Assert: Verify employee details
        assertEquals(1L, result.getId());
        assertEquals("Rahul", result.getName());
        assertEquals("rahul@gmail.com", result.getEmail());
        assertEquals("IT", result.getDepartment());
        assertEquals(50000.0, result.getSalary());
    }
    @Test
    void testGetEmployeeByIdNotFound(){

        //Arrange: Mock an employee ID that does not exist
        Long employeeId=99999L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //Act and Assert:Verify that the exception is thrown
        EmployeeNotFoundException exception =assertThrows( EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));

        //Verify the exception message
        assertEquals("Employee not found with ID:99999",exception.getMessage());
    }
    @Test
    void testUpdateEmployee(){

        //Arrange: Create an existing employee
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setName("Rahul");
        existingEmployee.setEmail("rahul@gmail.com");
        existingEmployee.setDepartment("IT");
        existingEmployee.setSalary(50000.0);

        //Create updated employee details
        Employee updatedDetails = new Employee();
        updatedDetails.setName("Rahul Sharma");
        updatedDetails.setEmail("rahul.sharma@gmail.com");
        updatedDetails.setDepartment("Finance");
        updatedDetails.setSalary(65000.0);

        //Mock repository responses
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        //Act: Update the employee
        Employee result = employeeService.updateEmployee(1L, updatedDetails);

        //Assert: Verify updated details
        assertEquals(1L, result.getId());
        assertEquals("Rahul Sharma", result.getName());
        assertEquals("rahul.sharma@gmail.com", result.getEmail());
        assertEquals("Finance", result.getDepartment());
        assertEquals(65000.0, result.getSalary());
    }
    @Test
    void testDeleteEmployee(){

        //Arrange:Create an existing employee
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);

        //Mock the repository response
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        //Act: Delete the employee
        employeeService.deleteEmployee(1L);

        //Assert: Verify that delete was called exactly once
        verify(employeeRepository, times(1)).deleteById(1L);
    }
    @Test
    void testDeleteEmployeeNotFound(){
        //Arrange EmployeeId does not exist
        Long employeeId=99999L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //Act and Assert: Verify exception is thrown
        EmployeeNotFoundException exception =assertThrows( EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));

        //Verify exception message
        assertEquals("Employee not found with ID:99999",exception.getMessage());

        //Verify that no deletion was performed
        verify(employeeRepository,never()).deleteById(employeeId);
    }
    @Test
    void testUpdateEmployeeNotFound(){

        //Arrange:Employee ID does not exist
        Long employeeId=99999L;

        //Create employee Details for updating
        Employee updatedDetails = new Employee();
        updatedDetails.setName("Rahul Sharma");
        updatedDetails.setEmail("rahul.sharma@gmail.com");
        updatedDetails.setDepartment("Finance");
        updatedDetails.setSalary(65000.0);

        //Mock repository response
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        //Act and Assert: Verify exception is thrown
        EmployeeNotFoundException exception =assertThrows( EmployeeNotFoundException.class, () -> employeeService.updateEmployee(employeeId, updatedDetails));

        //Verify exception message
        assertEquals("Employee not found with ID:99999",exception.getMessage());

        //Verify that no employee was saved
        verify(employeeRepository,never()).save(updatedDetails);
        
    }
}

