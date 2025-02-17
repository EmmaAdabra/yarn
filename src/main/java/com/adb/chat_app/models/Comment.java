package com.adb.chat_app.models;

public class Comment {
    private int commentId;
    private Integer postId;
    private int userId;
    private String comment;

    private String time;

    public Comment() {
    }

    public Comment(int postId, int userId, String comment, String createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.time = createdAt;
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

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
