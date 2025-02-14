package com.adb.chat_app.utils;

public enum WebPagePaths {
    REGISTER(getPathDir() + "registerUser.jsp"),
    LOGIN(getPathDir() + "login.jsp"),
    DASHBOARD(getPathDir() + "dashboard.jsp"),
    EDIT_USER_PROFILE(getPathDir() + "editUserProfile.jsp");

    private final String pagePath;
    private static final  String  PATH_DIR = "WEB-INF/views/";

    WebPagePaths(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getPagePath(){
        return pagePath;
    }

    public static String getPathDir(){
        return PATH_DIR;
    }
}

