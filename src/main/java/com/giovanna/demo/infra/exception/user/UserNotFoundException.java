package com.giovanna.demo.infra.exception.user;

public class UserNotFoundException extends RuntimeException {
    private static final String defaultMessage = "user not found";

    public UserNotFoundException() {
        super(defaultMessage);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
