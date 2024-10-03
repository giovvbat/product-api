package com.giovanna.demo.infra.exception.user;

public class UnauthorizedCredentialsException extends RuntimeException {
    private static final String defaultMessage = "unauthorized credentials";

    public UnauthorizedCredentialsException() {
        super(defaultMessage);
    }

    public UnauthorizedCredentialsException(String message) {
        super(message);
    }
}
