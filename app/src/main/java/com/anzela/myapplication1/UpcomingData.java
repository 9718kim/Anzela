package com.anzela.myapplication1;

public class UpcomingData {

    private String Title;
    private String Team;
    private String Date;

    public UpcomingData(String Title, String Team, String Date) {
        this.Title = Title;
        this.Team = Team;
        this.Date = Date;

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
