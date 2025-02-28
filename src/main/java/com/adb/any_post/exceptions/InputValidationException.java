package com.adb.any_post.exceptions;

public class InputValidationException extends Exception{
    public InputValidationException(String message) {
        super(message);
    }

    public InputValidationException() {
        super("Data validation error");
    }
}
