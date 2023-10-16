package com.example.rqchallenge.employees.error;

import java.util.Map;

public class AppError extends RuntimeException {
    private int statusCode;
    private String errorCode;
    private String errorMessage;
    private Map<String, Object> metadata;

    public static AppError badRequest(ErrorCode errorCode, String errorMessage) {
        return new AppError(400, errorCode.name(), errorCode.ErrorMessage(), null);
    }

    public static AppError internalError(ErrorCode errorCode) {
        return new AppError(500, errorCode.name(), errorCode.ErrorMessage(), null);
    }

    public AppError(int statusCode, String errorCode, String errorMessage, Map<String, Object> metadata) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.metadata = metadata;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
