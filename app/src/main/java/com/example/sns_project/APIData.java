package com.example.sns_project;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class APIData {
    public static boolean inPerforList = false, inTitle = false, inStartDate = false, inEndDate = false, inPlace = false;
    public static boolean inRealmName = false, inArea = false, inThumbnail = false, inSeqNum = false;
    public static boolean inPrice = false, inUrl = false, ingpsX = false, ingpsY = false, inplaceAddr = false;
    public static final String serviceKey = "nPNS96E9tPdBbuORe7jyzvIx9NxrNVmvAV1e5vh%2B2lItx%2F9mmlcqmEZeTCt%2FYL84UEsuGXUO3fFhuTL8kG4Tzg%3D%3D";
    public static final int rows = 200;
    public static ArrayList<PerformanceInfo> performanceInfos = new ArrayList<>();
    public static ArrayList<String> title = new ArrayList<>();
    public static ArrayList<String> startDate = new ArrayList<>();
    public static ArrayList<String> endDate = new ArrayList<>();
    //public static ArrayList<String> place = new ArrayList<>();
    public static String place;
    public static ArrayList<String> realmName = new ArrayList<>();
    public static ArrayList<String> area = new ArrayList<>();
    public static ArrayList<String> thumbNail = new ArrayList<>();
    public static ArrayList<String> seqNum = new ArrayList<>();
    public static ArrayList<String> gpsX = new ArrayList<>();
    public static ArrayList<String> gpsY = new ArrayList<>();
    public static PerformanceDetailInfo performanceDetailInfo;
    public static String detailPrice;
    public static String detailUrl;
    public static String detailAddr;
    public static String errMsg = "";
    public APIData() {};

    public static ArrayList<PerformanceInfo> getPerformanceInfos(){
        return performanceInfos;
    }

    public static void getAllData(){
        SimpleDateFormat ydf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        SimpleDateFormat ydf2 = new SimpleDateFormat("yyyy", Locale.KOREA);
        String sDate = ydf.format(new Date());
        String eDate = ydf2.format(new Date())+"1231";
        try{
            URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period?"
                    + "serviceKey="+serviceKey+"&from="+sDate+"&to="+eDate+"&cPage=1&rows="+rows+"&place=&gpsxfrom=&gpsyfrom=&gpsxto=&gpsyto=&keyword=&sortStdr=3"
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
                        /*if(parser.getName().equals("place")){
                            inPlace = true;
                        }*/
                        if(parser.getName().equals("realmName")){
                            inRealmName = true;
                        }
                        if(parser.getName().equals("area") && !parser.getName().contains("/")) {
                            inArea = true;
                        }
                        if (parser.getName().equals("gpsX")){
                            ingpsX = true;
                        }
                        if (parser.getName().equals("gpsY")){
                            ingpsY = true;
                        }
                        if(parser.getName().equals("thumbnail")) {
                            inThumbnail = true;
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inSeqNum){
                            seqNum.add(parser.getText());
                            inSeqNum = false;
                        }
                        if(inTitle){
                            title.add(parser.getText());
                            inTitle = false;
                        }
                        if(inStartDate){
                            startDate.add(parser.getText());
                            inStartDate = false;
                        }
                        if(inEndDate){
                            endDate.add(parser.getText());
                            inEndDate = false;
                        }
                        /*if(inPlace){
                            place.add(parser.getText());
                            inPlace = false;
                        }*/
                        if(inRealmName){
                            realmName.add(parser.getText());
                            inRealmName = false;
                        }
                        if(inArea) {
                            String temp = parser.getText();
                            if(temp.contains("www.")){
                                temp = "";
                            }
                            area.add(temp);
                            inArea = false;
                        }
                        if(ingpsX) {
                            gpsX.add(parser.getText());
                            ingpsX = false;
                        }
                        if(ingpsY) {
                            gpsY.add(parser.getText());
                            ingpsY = false;
                        }
                        if(inThumbnail) {
                            thumbNail.add(parser.getText());
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
        setData();
    }

    public static void getDetailData(String seqNum){
        try{
            URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?serviceKey="+serviceKey+"&seq="+seqNum);
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);
            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("place")){
                            inPlace = true;
                        }
                        if (parser.getName().equals("price")) {
                            inPrice = true;
                        }
                        if (parser.getName().equals("url")) {
                            inUrl = true;
                        }
                        if (parser.getName().equals("placeAddr")) {
                            inplaceAddr = true;
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inPlace){
                            place = parser.getText();
                            inPlace = false;
                        }
                        if (inPrice) {
                            detailPrice = parser.getText();
                            inPrice = false;
                        }
                        if (inUrl) {
                            detailUrl = parser.getText();
                            inUrl = false;
                        }
                        if (inplaceAddr) {
                            detailAddr = parser.getText();
                            inplaceAddr = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            errMsg = e.toString();
        }
        setDetailData();
    }

    public static PerformanceDetailInfo getDetailInfo(){
        return performanceDetailInfo;
    }

    public static void setDetailData(){
        performanceDetailInfo = new PerformanceDetailInfo(detailPrice, detailUrl, place, detailAddr);
    }

    public static void setData(){
        for(int i=0; i<rows; i++){
            PerformanceInfo performanceInfo = new PerformanceInfo(seqNum.get(i), title.get(i), startDate.get(i), endDate.get(i),
                                                                    realmName.get(i), area.get(i), thumbNail.get(i),
                                                                    gpsX.get(i), gpsY.get(i));
            performanceInfos.add(performanceInfo);
        }
    }
}
