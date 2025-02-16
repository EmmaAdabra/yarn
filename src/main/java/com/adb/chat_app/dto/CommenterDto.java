package com.adb.chat_app.dto;

import com.adb.chat_app.models.Comment;

public class CommenterDto {
    private String name;
    private String pfp;
    private Comment comment;

    public CommenterDto(String name, String pfp, Comment comment) {
        this.name = name;
        this.pfp = pfp;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
