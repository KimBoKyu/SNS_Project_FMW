package com.example.sns_project;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class DataInfo {
    public static boolean inPerforList = false, inTitle = false, inStartDate = false, inEndDate = false, inPlace = false;
    public static boolean inRealmName = false, inArea = false, inThumbnail = false, inSeqNum = false;
    public static final String serviceKey = "nPNS96E9tPdBbuORe7jyzvIx9NxrNVmvAV1e5vh%2B2lItx%2F9mmlcqmEZeTCt%2FYL84UEsuGXUO3fFhuTL8kG4Tzg%3D%3D";
    public static ArrayList<String> title= new ArrayList<>();
    public static ArrayList<String> startDate = new ArrayList<>();
    public static ArrayList<String> endDate = new ArrayList<>();
    public static ArrayList<String> place = new ArrayList<>();
    public static ArrayList<String> realmName = new ArrayList<>();
    public static ArrayList<String> area = new ArrayList<>();
    public static ArrayList<String> thumbNail = new ArrayList<>();
    public static ArrayList<String> seqNum = new ArrayList<>();
    public static String temp = "";
    public static String errMsg = "";
    public DataInfo() {};

    public static ArrayList<String> getTitle(){
        return title;
    }
    public static ArrayList<String> getStartDate(){
        return startDate;
    }
    public static ArrayList<String> getEndDate(){
        return endDate;
    }
    public static ArrayList<String> getPlace(){
        return place;
    }
    public static ArrayList<String> getRealmName(){
        return realmName;
    }
    public static ArrayList<String> getArea(){
        return area;
    }
    public static ArrayList<String> getThumbNail(){
        return thumbNail;
    }
    public static ArrayList<String> getSeqNum() { return seqNum; }

    public static void getData(){
        try{
            URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period?"
                    + "serviceKey="+serviceKey+"&from=20180101&to=20180819&cPage=1&rows=50&place=&gpsxfrom=&gpsyfrom=&gpsxto=&gpsyto=&keyword=&sortStdr=1"
            );
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);
            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("seq")){
                            inSeqNum = true;
                        }
                        if(parser.getName().equals("title")){
                            inTitle = true;
                        }
                        if(parser.getName().equals("startDate")){
                            inStartDate = true;
                        }
                        if(parser.getName().equals("endDate")){
                            inEndDate = true;
                        }
                        if(parser.getName().equals("place")){
                            inPlace = true;
                        }
                        if(parser.getName().equals("realmName")){
                            inRealmName = true;
                        }
                        if(parser.getName().equals("area") && !parser.getName().contains("/")) {
                            inArea = true;
                            Log.e("DFDFD ", parser.getName());
                        }
                        if(parser.getName().equals("thumbnail")) {
                            inThumbnail = true;
                        }
                        if(parser.getName().equals("message")){

                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inTitle){
                            temp = parser.getText();
                            System.out.println(temp);
                            title.add(temp);
                            inTitle = false;
                        }
                        if(inStartDate){
                            temp = parser.getText();
                            startDate.add(temp);
                            inStartDate = false;
                        }
                        if(inEndDate){
                            temp = parser.getText();
                            endDate.add(temp);
                            inEndDate = false;
                        }
                        if(inPlace){
                            temp = parser.getText();
                            place.add(temp);
                            inPlace = false;
                        }
                        if(inRealmName){
                            temp = parser.getText();
                            realmName.add(temp);
                            inRealmName = false;
                        }
                        if(inArea) {
                            temp = parser.getText();
                            if(temp.contains("www.")){
                                temp = "";
                            }
                            area.add(temp);
                            inArea = false;
                        }
                        if(inThumbnail) {
                            area.add(temp);
                            thumbNail.add(temp);
                            inThumbnail = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("perforList")){
                            inPerforList = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            errMsg = e.toString();
        }
    }
}
