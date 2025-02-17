package com.adb.chat_app.utils;

public class Response<T> {
    private int status;
    private String message;
    private T data;

    public Response() {
    }

    public Response(int status_code, String message, T data) {
        this.status = status_code;
        this.message = message;
        this.data = data;
    }

    public Response(int status_code, String message) {
        this.status = status_code;
        this.message = message;

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
