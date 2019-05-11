package com.itsight.advice;

public class SecCustomValidationException extends Exception{

    private String internalCode;

    public SecCustomValidationException(String message) {
        super(message);
    }

    public SecCustomValidationException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public String getInternalCode() {
        return internalCode;
    }
}
