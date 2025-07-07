package com.github.guilhermecustodionieto.finance_api.exceptions;

public class ErrorResponse {
    private int status;
    private String message;
    private String path;

    public ErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
