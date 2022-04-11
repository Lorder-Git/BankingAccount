package com.banking.cqrs.core.exceptions;

public class ConcurrencyExceptions extends RuntimeException {

    public ConcurrencyExceptions(String message) {
        super(message);
    }
}
