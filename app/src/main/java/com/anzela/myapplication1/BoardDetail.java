package com.anzela.myapplication1;

public class BoardDetail {
    public int id;
    public String title;
    public String content;
    public int cruCnt;
    public String startDate;
    public String startPoint;
    public String startLat;
    public String startLng;
    public String endPoint;
    public String endLat;
    public String endLng;
    public int cmtCnt;
    public String regDate;
    public User user;
    public Comments Comments;

    public BoardDetail(int id, String title, String content, int cruCnt, String startDate, String startPoint, String startLat,
                       String startLng, String endPoint, String endLat, String endLng, int cmtCnt, String regDate, User user, Comments Comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.cruCnt = cruCnt;
        this.startDate = startDate;
        this.startPoint = startPoint;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endPoint = endPoint;
        this.endLat = endLat;
        this.endLng = endLng;
        this.cmtCnt = cmtCnt;
        this.regDate = regDate;
        this.user = user;
        this.Comments = Comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCruCnt() {
        return cruCnt;
    }

    public void setCruCnt(int cruCnt) {
        this.cruCnt = cruCnt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLng() {
        return startLng;
    }

    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLng() {
        return endLng;
    }

    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }

    public int getCmtCnt() {
        return cmtCnt;
    }

    public void setCmtCnt(int cmtCnt) {
        this.cmtCnt = cmtCnt;
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

    public Comments getComments() {
        return Comments;
    }

    public void setComments(Comments comments) {
        Comments = comments;
    }
}
