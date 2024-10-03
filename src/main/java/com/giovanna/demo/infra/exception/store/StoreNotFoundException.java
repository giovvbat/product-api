package com.giovanna.demo.infra.exception.store;

public class StoreNotFoundException extends RuntimeException {
    private static final String defaultMessage = "store not found";

    public StoreNotFoundException() {
        super(defaultMessage);
    }

    public StoreNotFoundException(String message) {
        super(message);
    }
}
