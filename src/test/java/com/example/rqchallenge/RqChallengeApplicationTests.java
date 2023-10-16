package com.example.rqchallenge;

import com.example.rqchallenge.employees.controller.EmployeeController;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeInput;
import com.example.rqchallenge.employees.service.EmployeeService;

import java.util.Arrays;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RqChallengeApplicationTests {
    @InjectMocks
    private EmployeeController employeeController = new EmployeeController(); // The service to be tested

    @Mock
    private EmployeeService employeeService; // Mock the EmployeeService

    @MockBean
    private RestTemplate restTemplate; // Mock the RestTemplate

    @BeforeEach
    public void init() {
        Employee e1 = new Employee();
        e1 = new Employee();
        e1.setId(0L);
        e1.setName("John");
        e1.setSalary(50000);
        e1.setAge(30L);

        Employee e2 = new Employee();
        e2 = new Employee();
        e2.setId(1L);
        e2.setName("Smith");
        e2.setSalary(10000);
        e2.setAge(22L);

        Employee e3 = new Employee();
        e3 = new Employee();
        e3.setId(2L);
        e3.setName("Tim");
        e3.setSalary(30000);
        e3.setAge(25L);

        List<Employee> getAllEmployeesResponse = Arrays.asList(e1, e2);

        Mockito.when(employeeService.getAllEmployees()).thenReturn(getAllEmployeesResponse);

        Mockito.when(employeeService.getEmployeesByNameSearch("John")).thenReturn(Arrays.asList(e1));
        Mockito.when(employeeService.getEmployeesByNameSearch("Smith")).thenReturn(Arrays.asList(e2));
        Mockito.when(employeeService.getEmployeesByNameSearch("Alex")).thenReturn(Arrays.asList());

        Mockito.when(employeeService.getEmployeeById("0")).thenReturn(e1);
        Mockito.when(employeeService.getEmployeeById("1")).thenReturn(e2);
        Mockito.when(employeeService.getEmployeeById("2")).thenReturn(null);

        Mockito.when(employeeService.getHighestSalaryOfEmployees()).thenReturn(50000);

        Mockito.when(employeeService.getTop10HighestEarningEmployeeNames()).thenReturn(Arrays.asList(e1, e2, e1, e2, e1, e2, e1, e2, e1, e2));
        Mockito.when(employeeService.createEmployee("Tim", "30000", "25")).thenReturn(e3);
        Mockito.when(employeeService.deleteEmployee("0")).thenReturn("success");
        Mockito.when(employeeService.deleteEmployee("4")).thenReturn("not found");
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        // Create mock data for the external API response

        // Define the behavior of the mocked RestTemplate


        // Perform the test
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<Employee> employees = response.getBody();

        // Assertions
        assertEquals(2, employees.size());
        assertEquals(0L, employees.get(0).getId());
        assertEquals("John", employees.get(0).getName());
        assertEquals(50000, employees.get(0).getSalary());
        assertEquals(30L, employees.get(0).getAge());

        assertEquals(1L, employees.get(1).getId());
        assertEquals("Smith", employees.get(1).getName());
        assertEquals(10000, employees.get(1).getSalary());
        assertEquals(22L, employees.get(1).getAge());
    }

    @Test
    public void getEmployeesByNameSearch() throws Exception {
        // Perform the test
        ResponseEntity<List<Employee>> response1 = employeeController.getEmployeesByNameSearch("John");
        assertEquals(response1.getStatusCode(), HttpStatus.OK);
        List<Employee> employees = response1.getBody();

        // Assertions
        assertEquals(1, employees.size());
        assertEquals(0L, employees.get(0).getId());
        assertEquals("John", employees.get(0).getName());
        assertEquals(50000, employees.get(0).getSalary());
        assertEquals(30L, employees.get(0).getAge());

        ResponseEntity<List<Employee>> response2 = employeeController.getEmployeesByNameSearch("Smith");
        assertEquals(response2.getStatusCode(), HttpStatus.OK);
        employees = response2.getBody();

        assertEquals(1, employees.size());
        assertEquals(1L, employees.get(0).getId());
        assertEquals("Smith", employees.get(0).getName());
        assertEquals(10000, employees.get(0).getSalary());
        assertEquals(22L, employees.get(0).getAge());

        ResponseEntity<List<Employee>> response3 = employeeController.getEmployeesByNameSearch("Alex");
        assertEquals(response3.getStatusCode(), HttpStatus.OK);
        employees = response3.getBody();
        assertEquals(0, employees.size());
    }

    @Test
    public void getEmployeeById() throws Exception {
        // Perform the test
        ResponseEntity<Employee> response1 = employeeController.getEmployeeById("0");
        assertEquals(response1.getStatusCode(), HttpStatus.OK);
        Employee employee = response1.getBody();

        // Assertions
        assertNotNull(employee);
        assertEquals(0L, employee.getId());
        assertEquals("John", employee.getName());
        assertEquals(50000, employee.getSalary());
        assertEquals(30L, employee.getAge());

        ResponseEntity<Employee> response2 = employeeController.getEmployeeById("1");
        assertEquals(response2.getStatusCode(), HttpStatus.OK);
        employee = response2.getBody();

        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Smith", employee.getName());
        assertEquals(10000, employee.getSalary());
        assertEquals(22L, employee.getAge());

        ResponseEntity<Employee> response3 = employeeController.getEmployeeById("2");
        assertEquals(response3.getStatusCode(), HttpStatus.OK);
        employee = response3.getBody();
        assertNull(employee);
    }

    @Test
    public void getHighestSalaryOfEmployees() throws Exception {
        // Perform the test
        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), 50000);
    }

    @Test
    public void getTopTenHighestEarningEmployeeNames() throws Exception {
        // Perform the test
        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(), 10);
        assertEquals(response.getBody().get(0), "John");
        assertEquals(response.getBody().get(1), "Smith");
    }

    @Test
    public void createEmployee() throws Exception {
        EmployeeInput input = new EmployeeInput();
        input.setName("Tim");
        input.setAge("25");
        input.setSalary("30000");

        ResponseEntity<Employee> response = employeeController.createEmployee(input);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getId(), 2);
        assertEquals(response.getBody().getName(), "Tim");
        assertEquals(response.getBody().getAge(), 25);
        assertEquals(response.getBody().getSalary(), 30000);
    }

    @Test
    public void deleteEmployee() throws Exception {
        ResponseEntity<String> response = employeeController.deleteEmployeeById("0");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Employee with ID 0 has been deleted");

        response = employeeController.deleteEmployeeById("4");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody(), "Failed to delete employee");
    }
}