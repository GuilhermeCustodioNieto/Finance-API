package com.github.guilhermecustodionieto.finance_api.exceptions.generics;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName, String metadata) {
        super("No " + entityName + " found with " + metadata + " data.");
    }
}
