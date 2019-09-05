package com.example.sns_project;

import java.io.Serializable;

public class PerformanceInfo implements Serializable {
    private String title;
    private String startDate;
    private String endDate;
    private String place;
    private String realmName;
    private String area;
    private String thumbNail;
    private String seqNum;
    private String gpsX;
    private String gpsY;
    private String distance;

    public PerformanceInfo(String seqNum, String title, String startDate, String endDate, String realmName, String area, String thumbNail,
                           String gpsX, String gpsY){
        this.seqNum = seqNum;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = "";
        this.realmName = realmName;
        this.area = area;
        this.thumbNail = thumbNail;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.distance = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }

    public void setDistance(String distance){
        this.distance = distance;
    }

    public String getDistance(){
        return distance;
    }
}
