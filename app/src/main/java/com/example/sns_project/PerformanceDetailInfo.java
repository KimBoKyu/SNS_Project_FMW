package com.example.sns_project;

import java.io.Serializable;

public class PerformanceDetailInfo implements Serializable {
    private String price;
    private String url;
    private String placeAddr;
    private String place;

    public PerformanceDetailInfo(String price, String url, String place, String placeAddr){
        this.price = price;
        this.url = url;
        this.place = place;
        this.placeAddr = placeAddr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlace(){
        return place;
    }

    public void setPlace(String place){
        this.place = place;
    }
    public String getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        this.placeAddr = placeAddr;
    }
}
