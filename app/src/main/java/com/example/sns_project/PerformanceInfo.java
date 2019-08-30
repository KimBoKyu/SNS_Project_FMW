package com.example.sns_project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PerformanceInfo implements Serializable {
    private String title;
    private String startDate;
    private String endDate;
    private String place;
    private String realmName;
    private String area;
    private String thumbNail;
    private String seqNum;

    public PerformanceInfo(String seqNum, String title, String startDate, String endDate, String place, String realmName, String area, String thumbNail){
        this.seqNum = seqNum;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.realmName = realmName;
        this.area = area;
        this.thumbNail = thumbNail;
    }

    public Map<String, Object> getPerformanceInfo(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("seqNum",seqNum);
        docData.put("title",title);
        docData.put("startDate",startDate);
        docData.put("endDate",endDate);
        docData.put("place",place);
        docData.put("realmName",realmName);
        docData.put("area", area);
        docData.put("thumbNail", thumbNail);
        return  docData;
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
}
