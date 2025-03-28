package com.adb.yarn.models;

public class Like {
    private Integer userId;
    private int postId;

    public Like(int postId, Integer userId) {
        this.userId = userId;
        this.postId = postId;
    }

    public Like() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
