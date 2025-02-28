package com.adb.any_post.dao.commentDao;

public enum CommentSqlScriptsPath {

    INSERT_COMMENT(getUserScriptDir() + "insert_comment.sql"),
    GET_POST_COMMENTS(getUserScriptDir() + "get_post_comments.sql"),
    DELETE_COMMENT(getUserScriptDir() + "delete_comment.sql");

    private String path;

    private final static String sqrScriptsDir = "sql_scripts/commentscripts/";
    CommentSqlScriptsPath(String path) {
        this.path = path;
    }

    public static String  getUserScriptDir() {
        return sqrScriptsDir;
    }

    public String getPath() {
        return path;
    }
}
