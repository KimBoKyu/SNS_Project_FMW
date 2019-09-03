package com.example.sns_project;

import java.io.Serializable;

public class PerformanceDetailInfo implements Serializable {
    private String price;
    private String url;
    private String placeAddr;

    public PerformanceDetailInfo(String price, String url, String placeAddr){
        this.price = price;
        this.url = url;
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

    public String getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        this.placeAddr = placeAddr;
    }
}
