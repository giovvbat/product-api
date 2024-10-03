package com.giovanna.demo.enums;

public enum OperationType {
    ENTRY,
    EXIT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
