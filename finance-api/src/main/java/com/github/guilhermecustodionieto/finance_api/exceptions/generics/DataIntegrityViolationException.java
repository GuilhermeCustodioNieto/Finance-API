package com.github.guilhermecustodionieto.finance_api.exceptions.generics;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String entity) {
        super("Not all data has been filled in for the entity " + entity + '.');
    }
}
