package com.giovanna.demo.infra.exception.role;

public class RoleNameAlreadyTakenException extends RuntimeException {
    private static final String defaultMessage = "role name already taken";

    public RoleNameAlreadyTakenException() {
        super(defaultMessage);
    }

    public RoleNameAlreadyTakenException(String message) {
        super(message);
    }
}
