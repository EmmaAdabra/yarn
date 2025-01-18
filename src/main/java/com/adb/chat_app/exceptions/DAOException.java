package com.adb.chat_app.exceptions;

public class DAOException extends Exception {
    private String message;

    public DAOException(String message, Throwable cause){
        super(message, cause);
    }
}
