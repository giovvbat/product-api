package com.giovanna.demo.infra.exception.user;

public class EmailAlreadyTakenException extends RuntimeException {
    private static final String defaultMessage = "email already taken";

    public EmailAlreadyTakenException() {
        super(defaultMessage);
    }

    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
