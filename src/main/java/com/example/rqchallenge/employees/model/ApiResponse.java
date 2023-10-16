package com.example.rqchallenge.employees.model;

import java.util.List;

public class ApiResponse {
    private String status;
    private List<EmployeeData> data;


    public ApiResponse() {
    }

    public ApiResponse(String status, List<EmployeeData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmployeeData> getData() {
        return data;
    }

    public void setData(List<EmployeeData> data) {
        this.data = data;
    }
}

