package com.example.persistence.exception;

public class TickerNotFoundException extends RuntimeException {
    public TickerNotFoundException(String message) {
        super(message);
    }
}
