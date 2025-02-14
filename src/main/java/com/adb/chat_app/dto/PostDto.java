package com.adb.chat_app.dto;

import com.adb.chat_app.models.Post;

public class PostDto extends Post {
    private PosterDto posterData;
    private String postDate;

    public PosterDto getPosterData() {
        return posterData;
    }

    public void setPosterData(PosterDto posterData) {
        this.posterData = posterData;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
