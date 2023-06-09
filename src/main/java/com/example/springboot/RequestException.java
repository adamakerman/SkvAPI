package com.example.springboot;


public class RequestException extends Exception {
    public RequestException(String errorMessage) {
        super(errorMessage);
    }
}