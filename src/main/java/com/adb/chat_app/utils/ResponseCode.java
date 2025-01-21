package com.adb.chat_app.utils;

public enum ResponseCode {
    SUCCESS(200),
    RESOURCE_CREATED(201),
    VALIDATION_ERROR(400),
    RESOURCE_NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
