package com.adb.chat_app.utils;

public class StringUtil {
    public static String getUserInitial(String firstName, String lastName){
        return (firstName.charAt(0) + String.valueOf(lastName.charAt(0)));
    }

    public static String getUSerFullName(String firstName, String lastName){
        return firstName + " " + lastName;
    }
}
