package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.EmployeeInput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.rqchallenge.employees.service.EmployeeService;
import com.example.rqchallenge.employees.model.Employee;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController implements IEmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Getting all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Override
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        List<Employee> employees = employeeService.getEmployeesByNameSearch(searchString);
        return ResponseEntity.ok(employees);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        Integer highestSalary = employeeService.getHighestSalaryOfEmployees();
        return ResponseEntity.ok(highestSalary);
    }

    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employees = employeeService.getTop10HighestEarningEmployeeNames();
        List<String> employeeNames = employees.stream().map(Employee::getName).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(employeeNames);
    }

    @Override
    @PostMapping()
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeInput employeeInput) {
        String name = employeeInput.getName();
        String salary = employeeInput.getSalary();
        String age = employeeInput.getAge();
        Employee newEmployee = employeeService.createEmployee(name, salary, age);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        String result = employeeService.deleteEmployee(id);
        if ("success".equals(result)) {
            return ResponseEntity.ok("Employee with ID " + id + " has been deleted");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete employee");
        }
    }
}
