package com.anzela.myapplication1;

public class Comments {
    public int id;
    public String content;
    public int depth;
    public String regDate;
    public User user;

    public Comments(int id, String content, int depth, String regDate, User user) {
        this.id = id;
        this.content = content;
        this.depth = depth;
        this.regDate = regDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
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