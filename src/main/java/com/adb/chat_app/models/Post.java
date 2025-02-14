package com.adb.chat_app.models;

import java.sql.Timestamp;

public class Post {
    private int postId;
    private int userId;
    private String postTile;
    private String content;
    private String media;
    private Timestamp createdAt;

    public Post(int postId, int userId, String postTile, String content, String media, Timestamp createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.postTile = postTile;
        this.content = content;
        this.media = media;
        this.createdAt = createdAt;
    }

    public Post(int userId, String postTile, String content, String media) {
        this.userId = userId;
        this.postTile = postTile;
        this.content = content;
        this.media = media;
    }

    public Post() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getPostTile() {
        return postTile;
    }

    public void setPostTile(String postTile) {
        this.postTile = postTile;
    }
}
