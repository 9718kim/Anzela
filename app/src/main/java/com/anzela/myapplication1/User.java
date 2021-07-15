package com.anzela.myapplication1;

public class User {
    public String uid;
    public String profileUrl;

    public User(String uid, String profileUrl) {
        this.uid = uid;
        this.profileUrl = profileUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
