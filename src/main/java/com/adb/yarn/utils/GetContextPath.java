package com.adb.yarn.utils;

public class GetContextPath {
    private static String contextPath;

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String path) {
        GetContextPath.contextPath = path;
    }
}
