package com.giovanna.demo.infra.exception.stock;

public class NoStockOperationsFoundException extends RuntimeException {
    private static final String defaultMessage = "no stock operations found";

    public NoStockOperationsFoundException() {
        super(defaultMessage);
    }

    public NoStockOperationsFoundException(String message) {
        super(message);
    }
}
