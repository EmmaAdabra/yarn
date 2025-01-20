package com.adb.chat_app.dao.userdao;

public enum UserSqlScriptsPath {

    INSERT_USER_SCRIPT(getUserScriptDir() + "insert_user.sql");

    private String path;

    private final static String sqrScriptsDir = "sql_scripts/userscripts/";
    UserSqlScriptsPath(String path) {
        this.path = path;
    }

    public static String  getUserScriptDir() {
        return sqrScriptsDir;
    }

    public String getPath() {
        return path;
    }
}
