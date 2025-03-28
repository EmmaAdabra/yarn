package com.adb.yarn.models;

public class Comment {
    private Integer postId;
    private int userId;
    private String comment;

    public Comment() {
    }

    public Comment(int postId, int userId, String comment) {
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
