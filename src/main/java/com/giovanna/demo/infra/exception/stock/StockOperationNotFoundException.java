package com.giovanna.demo.infra.exception.stock;

public class StockOperationNotFoundException extends RuntimeException {
    private static final String defaultMessage = "stock operation not found";

    public StockOperationNotFoundException() {
        super(defaultMessage);
    }

    public StockOperationNotFoundException(String message) {
        super(message);
    }
}
