package com.adb.any_post.dao.likesDao;

public enum LikeSqlScriptsPath {

    INSERT_LIKE_SCRIPT(getLikeScriptDir() + "insert_likes.sql"),
    GET_USER_LIKED_POSTS(getLikeScriptDir() + "get_user_liked_posts.sql"),
    DELETE_LIKE(getLikeScriptDir() + "delete_like.sql");

    private String path;

    private final static String sqrScriptsDir = "sql_scripts/likescripts/";
    LikeSqlScriptsPath(String path) {
        this.path = path;
    }

    public static String  getLikeScriptDir() {
        return sqrScriptsDir;
    }

    public String getPath() {
        return path;
    }
}
