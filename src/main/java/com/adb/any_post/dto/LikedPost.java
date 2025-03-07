package com.adb.any_post.dto;

public class LikedPost {
    private int postId;
    private int likedId;

    public LikedPost() {
    }

    public LikedPost(int postId, int likedId) {
        this.postId = postId;
        this.likedId = likedId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getLikedId() {
        return likedId;
    }

    public void setLikedId(int likedId) {
        this.likedId = likedId;
    }
}
