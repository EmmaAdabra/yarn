package com.adb.yarn.dao.userdao;

public enum UserSqlScriptsPath {

    INSERT_USER_SCRIPT(getUserScriptDir() + "insert_user.sql"),
    GET_USER_BY_EMAIL(getUserScriptDir() + "get_user_by_email.sql"),
    INSERT_USER_PFP(getUserScriptDir() + "insert_user_pfp.sql"),
    HAS_USER_PFP(getUserScriptDir() + "has_user_pfp.sql"),
    FETCH_USER_PFP(getUserScriptDir() + "fetch_user_pfp.sql"),
    UPDATE_USER_BIO(getUserScriptDir() + "update_user_bio.sql"),
    GET_USER_BY_ID(getUserScriptDir() + "get_user_by_id.sql"),
    UPDATE_USER_PERSONAL_DATA(getUserScriptDir() + "update_personal_data.sql");
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
