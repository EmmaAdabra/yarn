package com.adb.any_post.dto;

public class CommentDto {
    private int commentId;
    private int userId;
    private String name;
    private String initial;
    private String pfp;
    private String time;
    private String  comment;

    public CommentDto() {
    }

    public CommentDto(int commentId, int userId, String name, String initial, String pfp, String time, String comment) {
        this.commentId = commentId;
        this.userId = userId;
        this.name = name;
        this.initial = initial;
        this.pfp = pfp;
        this.time = time;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}