package com.giovanna.demo.infra.exception.product;

public class NoProductsFoundException extends RuntimeException {
    private static final String defaultMessage = "no products found";

    public NoProductsFoundException() {
        super(defaultMessage);
    }

    public NoProductsFoundException(String message) {
        super(message);
    }
}
