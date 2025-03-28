package com.adb.yarn.exceptions;

public class InputValidationException extends Exception{
    public InputValidationException(String message) {
        super(message);
    }

    public InputValidationException() {
        super("Data validation error");
    }
}
