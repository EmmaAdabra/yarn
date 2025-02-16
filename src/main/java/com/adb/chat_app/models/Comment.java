package com.adb.chat_app.models;

public class Comment {
    private int commentId;
    private String postId;
    private int userId;
    private String commenterName;
    private String comment;

    public Comment() {
    }

    public Comment(String postId, int userId, String commenterName, String comment) {
        this.postId = postId;
        this.userId = userId;
        this.commenterName = commenterName;
        this.comment = comment;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
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
}
