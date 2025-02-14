package com.adb.chat_app.dto;

import java.io.Serializable;

public class SessionUserDTO implements Serializable {
    private String fName;
    private String lName;
    private String username;
    private String email;
    private String bio;
    private String pfp;
    private long userID;
    private String userFullName;
    private String UserInitial;
    private boolean hasPfp = false;
    private String pfpUrl;


    public SessionUserDTO(
            String fName, String lName, boolean hasPfp,
            String username, String bio, long userID,
            String userFullName, String initial
)
    {
        this.fName = fName;
        this.lName = lName;
        this.username = username;
        this.bio = bio;
        this.userID = userID;
        this.userFullName = userFullName;
        this.UserInitial = initial;
        this.hasPfp = hasPfp;
    }

    public SessionUserDTO() {
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserInitial() {
        return UserInitial;
    }

    public void setUserInitial(String initial) {
        this.UserInitial = initial;
    }

    public boolean isHasPfp() {
        return hasPfp;
    }

    public void setHasPfp(boolean hasPfp) {
        this.hasPfp = hasPfp;
    }

    public String getPfpUrl() {
        return pfpUrl;
    }

    public void setPfpUrl(String pfpUrl) {
        this.pfpUrl = pfpUrl;
    }
}
