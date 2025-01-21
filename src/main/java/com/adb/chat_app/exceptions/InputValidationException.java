package com.adb.chat_app.exceptions;

public class InputValidationException extends Exception{
    public InputValidationException(String message) {
        super(message);
    }

    public InputValidationException() {
        super("Data validation error");
    }
}
