package com.adb.chat_app.utils;

public class Response<T> {
    private int status_code;
    private String message;
    private T data;

    public Response(int status_code, String message, T data) {
        this.status_code = status_code;
        this.message = message;
        this.data = data;
    }

    public Response(int status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }
}
