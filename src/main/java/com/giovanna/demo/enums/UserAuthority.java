package com.giovanna.demo.enums;

public enum UserAuthority {
    ADMIN,
    EMPLOYEE,
    MANAGER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
