package com.giovanna.demo.infra.exception.stock;

public class InviableStockOperationException extends RuntimeException {
    private static final String defaultMessage = "exit exceeds stock limit";

    public InviableStockOperationException() {
        super(defaultMessage);
    }

    public InviableStockOperationException(String message) {
        super(message);
    }
}
