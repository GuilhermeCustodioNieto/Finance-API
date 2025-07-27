package com.github.guilhermecustodionieto.finance_api.exceptions.generics;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
