package com.adb.chat_app.exceptions;

public class ValidateDataException extends Exception{
    public ValidateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateDataException() {
        super("Data validation error");
    }
}
