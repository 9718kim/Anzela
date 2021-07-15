package com.anzela.myapplication1;

public class Comments {
    public String id;
    public String content;
    public String depth;
    public String regDate;
    public User user;

    public Comments(String id, String content, String depth, String regDate, User user) {
        this.id = id;
        this.content = content;
        this.depth = depth;
        this.regDate = regDate;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}