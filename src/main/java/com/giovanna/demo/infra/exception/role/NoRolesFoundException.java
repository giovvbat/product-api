package com.giovanna.demo.infra.exception.role;

public class NoRolesFoundException extends RuntimeException {
    private static final String defaultMessage = "no roles found";

    public NoRolesFoundException() {
        super(defaultMessage);
    }

    public NoRolesFoundException(String message) {
        super(message);
    }
}
