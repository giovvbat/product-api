package com.giovanna.demo.infra.exception.role;

public class RoleNotFoundException extends RuntimeException {
    private static final String defaultMessage = "role not found";

    public RoleNotFoundException() {
        super(defaultMessage);
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}