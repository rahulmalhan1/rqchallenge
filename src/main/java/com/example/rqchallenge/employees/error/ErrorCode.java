package com.example.rqchallenge.employees.error;

public enum ErrorCode {
    GET_ALL_EMPLOYEES_FAILED ("Error fetching data from the external api"),
    GET_EMPLOYEE_BY_ID_FAILED ("Error fetching data from the external api"),
    GET_EMPLOYEES_BY_NAME_SEARCH_FAILED ("Error fetching data from the external api"),
    CREATE_EMPLOYEE_FAILED ("Error creating employee"),
    UPDATE_EMPLOYEE_FAILED ("Error updating employee"),
    DELETE_EMPLOYEE_FAILED ("Error deleting employee");

    private final String errorMessage;

    private ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String ErrorMessage() {
        return errorMessage;
    }
}
