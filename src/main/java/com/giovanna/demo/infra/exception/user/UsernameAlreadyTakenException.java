package com.giovanna.demo.infra.exception.user;

public class UsernameAlreadyTakenException extends RuntimeException {
    private static final String defaultMessage = "username already taken";

    public UsernameAlreadyTakenException() {
        super(defaultMessage);
    }

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
