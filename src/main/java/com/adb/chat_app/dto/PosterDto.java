package com.adb.chat_app.dto;

public class PosterDto {
    private int id;
    private String name;
    private String firstName;
    private String initial;
    private String pfp;

    public PosterDto(int id, String name, String initial, String pfp) {
        this.id = id;
        this.name = name;
        this.initial = initial;
        this.pfp = pfp;
    }

    public PosterDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial(String first_name, String last_name) {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInitial() {
        return initial;
    }
}
