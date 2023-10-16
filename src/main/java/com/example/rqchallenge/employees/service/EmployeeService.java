package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.error.AppError;
import com.example.rqchallenge.employees.error.ErrorCode;
import com.example.rqchallenge.employees.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final String externalApiUrl = "https://dummy.restapiexample.com/api/v1";

    @Autowired
    private RestTemplate restTemplate;

    public List<Employee> getAllEmployees() {
        String reqId = MDC.get("requestId");
        log.info("Fetching data from the external api: {}", reqId);
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(externalApiUrl + "/employees", ApiResponse.class);
        String status = Objects.requireNonNull(responseEntity.getBody()).getStatus();
        if(!status.equals("success")) {
            log.error("Error fetching data from the external api");
            throw AppError.internalError(ErrorCode.GET_ALL_EMPLOYEES_FAILED);
        }
        List<EmployeeData> data = responseEntity.getBody().getData();
        if(data == null) {
            log.error("Error fetching data from the external api");
            throw AppError.internalError(ErrorCode.GET_ALL_EMPLOYEES_FAILED);
        }
        log.info("Data fetched successfully");
        return mapToEmployees(data);
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        log.info("Fetching data from the external api");
        List<Employee> allEmployees = getAllEmployees();
        if(allEmployees == null) {
            log.error("Error fetching data from the external api");
            throw AppError.internalError(ErrorCode.GET_EMPLOYEES_BY_NAME_SEARCH_FAILED);
        }
        log.info("Data fetched successfully");
        return allEmployees.stream()
                .filter(employee -> employee.getName().contains(searchString))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(String id) {
        log.info("Fetching data from the external api");
        ResponseEntity<GetEmployeeApiResponse> responseEntity = restTemplate.getForEntity(externalApiUrl + "/employee/" + id, GetEmployeeApiResponse.class);
        String status = Objects.requireNonNull(responseEntity.getBody()).getStatus();
        if(!status.equals("success")) {
            log.error("Error fetching data from the external api");
            throw AppError.internalError(ErrorCode.GET_EMPLOYEE_BY_ID_FAILED);
        }
        log.info("Data fetched successfully");
        return responseEntity.getBody().getData() != null ? Employee.fromData(responseEntity.getBody().getData()) : null;
    }

    public Integer getHighestSalaryOfEmployees() {
        List<Employee> allEmployees = getAllEmployees();
        return allEmployees.stream()
                .mapToInt(Employee::getSalary)
                .max()
                .orElse(0);
    }

    public List<Employee> getTop10HighestEarningEmployeeNames() {
        List<Employee> allEmployees = getAllEmployees();
        return allEmployees.stream()
                .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public Employee createEmployee(String name, String salary, String age) {
        EmployeeInput request = new EmployeeInput();
        request.setName(name);
        request.setSalary(salary);
        request.setAge(age);
        log.info("Creating employee");
        ResponseEntity<CreateEmployeeApiResponse> responseEntity = restTemplate.postForEntity(externalApiUrl + "/create", request, CreateEmployeeApiResponse.class);
        String status = Objects.requireNonNull(responseEntity.getBody()).getStatus();
        if(!status.equals("success")) {
            log.error("Error creating employee");
            throw AppError.internalError(ErrorCode.CREATE_EMPLOYEE_FAILED);
        }
        return responseEntity.getBody().getData() != null ? Employee.fromData(responseEntity.getBody().getData()) : null;
    }

    public String deleteEmployee(String id) {
        ResponseEntity<DeleteEmployeeApiResponse> responseEntity = restTemplate.exchange(
                externalApiUrl + "/delete/" + id,
                HttpMethod.DELETE,
                null,
                DeleteEmployeeApiResponse.class
        );
        String status = Objects.requireNonNull(responseEntity.getBody()).getStatus();
        if(!status.equals("success")) {
            log.error("Error deleting employee");
            throw AppError.internalError(ErrorCode.DELETE_EMPLOYEE_FAILED);
        }
        return responseEntity.getBody().getStatus();
    }

    public List<Employee> mapToEmployees(List<EmployeeData> dataList) {
        return dataList.stream()
                      .map(Employee::fromData)
                      .collect(Collectors.toList());
    }
}

