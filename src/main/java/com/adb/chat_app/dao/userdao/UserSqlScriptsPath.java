package com.adb.chat_app.dao.userdao;

public enum UserSqlScriptsPath {
    INSERT_USER_SCRIPT(getUserScriptDir() + "insert_user.sql");

    private static String USER_SCRIPT_DIR = "src/sql_scripts/user_scripts/";
    private String path;

    UserSqlScriptsPath(String path) {
        this.path = path;
    }

    public static String getUserScriptDir() {
        return USER_SCRIPT_DIR;
    }

    public String getPath() {
        return path;
    }
}
