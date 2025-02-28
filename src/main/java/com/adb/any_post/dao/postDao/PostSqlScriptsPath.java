package com.adb.any_post.dao.postDao;

public enum PostSqlScriptsPath {

    INSERT_USER_SCRIPT(getUserScriptDir() + "insert_post.sql"),
    GET_ALL_POSTS(getUserScriptDir() + "get_all_posts.sql"),
    DELETE_POST(getUserScriptDir() + "delete_post.sql");

    private String path;

    private final static String sqrScriptsDir = "sql_scripts/postscripts/";
    PostSqlScriptsPath(String path) {
        this.path = path;
    }

    public static String  getUserScriptDir() {
        return sqrScriptsDir;
    }

    public String getPath() {
        return path;
    }
}
