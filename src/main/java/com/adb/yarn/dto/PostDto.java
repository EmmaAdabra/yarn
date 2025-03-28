package com.adb.yarn.dto;

import com.adb.yarn.models.Post;

public class PostDto extends Post {
    private PosterDto posterData;
    private String longDate;

    public String getShortDate() {
        return shortDate;
    }

    public void setShortDate(String shortDate) {
        this.shortDate = shortDate;
    }

    private String shortDate;
    private int comment;
    private int likes;

    public PosterDto getPosterData() {
        return posterData;
    }

    public void setPosterData(PosterDto posterData) {
        this.posterData = posterData;
    }

    public String getLongDate() {
        return longDate;
    }

    public void setLongDate(String longDate) {
        this.longDate = longDate;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
