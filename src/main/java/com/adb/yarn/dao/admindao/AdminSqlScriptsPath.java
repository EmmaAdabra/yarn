package com.adb.yarn.dao.admindao;

public enum AdminSqlScriptsPath {

    GET_ADMIN_ID(getAdminScriptDir() + "fetch_admin_id.sql");

    private String path;

    private final static String sqrScriptsDir = "sql_scripts/adminscripts/";
    AdminSqlScriptsPath(String path) {
        this.path = path;
    }

    public static String getAdminScriptDir() {
        return sqrScriptsDir;
    }

    public String getPath() {
        return path;
    }
}
