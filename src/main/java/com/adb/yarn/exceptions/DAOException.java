package com.adb.yarn.exceptions;

public class DAOException extends Exception {
    private String message;

    public DAOException(String message, Throwable cause){
        super(message, cause);
    }
}
