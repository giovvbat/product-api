package com.giovanna.demo.infra.exception.product;

public class ProductNotFoundException extends RuntimeException {
    private static final String defaultMessage = "product not found";

    public ProductNotFoundException() {
        super(defaultMessage);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
