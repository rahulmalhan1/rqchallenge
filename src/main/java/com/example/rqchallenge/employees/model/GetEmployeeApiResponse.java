package com.example.rqchallenge.employees.model;

public class GetEmployeeApiResponse {
    private String status;
    private EmployeeData data;


    public GetEmployeeApiResponse() {
    }

    public GetEmployeeApiResponse(String status, EmployeeData data) {
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

