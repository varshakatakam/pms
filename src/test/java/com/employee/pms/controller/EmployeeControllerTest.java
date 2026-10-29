package com.employee.pms.controller;

import com.employee.pms.exception.EmployeeNotFoundException;
import com.employee.pms.exception.GlobalExceptionHandler;
import com.employee.pms.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.employee.pms.entity.Employee;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(employeeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAllEmployees() throws Exception {

        //Arrange: Create an employee
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);

        //Mock the service response

        when(employeeService.getAllEmployees())
                .thenReturn(List.of(employee));

        //Act andAsset: Test the GET API
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Rahul"))
                .andExpect(jsonPath("$[0].email").value("rahul@gmail.com"))
                .andExpect(jsonPath("$[0].department").value("IT"))
                .andExpect(jsonPath("$[0].salary").value(50000.0));
    }
    @Test
    void testGetEmployeeById() throws Exception {

        // Arrange: Create an employee
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);

        // Mock the service response
        when(employeeService.getEmployeeById(1L))
                .thenReturn(employee);

        // Act and Assert: Test GET /employees/1
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul"))
                .andExpect(jsonPath("$.email").value("rahul@gmail.com"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value(50000.0));
    }
    @Test
    void testGetEmployeeByIdNotFound() throws Exception {

        // Arrange: Simulate a nonexistent employee
        Long employeeId = 99999L;

        when(employeeService.getEmployeeById(employeeId))
                .thenThrow(new EmployeeNotFoundException(employeeId));

        // Act and Assert: Test GET /employees/99999
        mockMvc.perform(get("/employees/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Employee not found with ID:99999"));
    }
}
