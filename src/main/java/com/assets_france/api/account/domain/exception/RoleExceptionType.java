package com.assets_france.api.account.domain.exception;

public enum RoleExceptionType {
    ROLE_NOT_FOUND("ROLE_NOT_FOUND"),
    ROLE_FORBIDDEN("ROLE_FORBIDDEN");

    public String value;

    RoleExceptionType(String value) {
        this.value = value;
    }
}
