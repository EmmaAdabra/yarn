package com.adb.any_post.utils;

public class GetContextPath {
    private static String contextPath;

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String path) {
        GetContextPath.contextPath = path;
    }
}
