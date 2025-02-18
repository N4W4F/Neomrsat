package com.example.neomsr.ApiResponse;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
