package com.example.rqchallenge.employees.model;


public class CreateEmployeeApiResponse {
    private String status;
    private EmployeeData data;

    public CreateEmployeeApiResponse() {
    }

    public CreateEmployeeApiResponse(String status, EmployeeData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EmployeeData getData() {
        return data;
    }

    public void setData(EmployeeData data) {
        this.data = data;
    }
}

