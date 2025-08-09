package com.github.guilhermecustodionieto.finance_api.exceptions.generics;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
