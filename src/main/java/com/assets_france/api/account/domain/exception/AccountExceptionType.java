package com.assets_france.api.account.domain.exception;

public enum AccountExceptionType {
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND");

    public String value;

    AccountExceptionType(String value) {
        this.value = value;
    }
}
