package com.anzela.myapplication1;

public class NearData {

    private String NearTitle;
    private String NearTeam;
    private String NearDate;

    public NearData(String NearTitle, String NearTeam, String NearDate) {
        this.NearTitle = NearTitle;
        this.NearTeam = NearTeam;
        this.NearDate = NearDate;

    }

    @Override
    public String toString() {
        return "NearData{" +
                "NearTitle='" + NearTitle + '\'' +
                ", NearTeam=" + NearTeam +
                ", NearDate='" + NearDate + '\'' +
                '}';
    }

    public String getNearTitle() {
        return NearTitle;
    }

    public void setNearTitle(String NearTitle) {
        this.NearTitle = NearTitle;
    }

    public String getNearTeam() {
        return NearTeam;
    }

    public void setNearTeam(String NearTeam) {
        this.NearTeam = NearTeam;
    }

    public String getNearDate() {
        return NearDate;
    }

    public void setNearDate(String NearDate) {
        this.NearDate = NearDate;
    }
}