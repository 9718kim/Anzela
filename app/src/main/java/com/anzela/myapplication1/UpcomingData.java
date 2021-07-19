package com.anzela.myapplication1;

public class UpcomingData {

    private int id;
    private String Title;
    private String Team;
    private String Date;

    public UpcomingData(int id, String Title, String Team, String Date) {
        this.id = id;
        this.Title = Title;
        this.Team = Team;
        this.Date = Date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String Team) {
        this.Team = Team;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
}
