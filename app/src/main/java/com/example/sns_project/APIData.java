package com.example.sns_project;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class APIData {
    public static boolean inPerforList = false, inTitle = false, inStartDate = false, inEndDate = false, inPlace = false;
    public static boolean inRealmName = false, inArea = false, inThumbnail = false, inSeqNum = false, inContents1 = false;
    public static boolean inPrice = false, inUrl = false, ingpsX = false, ingpsY = false, inplaceAddr = false;
    public static final String serviceKey = "nPNS96E9tPdBbuORe7jyzvIx9NxrNVmvAV1e5vh%2B2lItx%2F9mmlcqmEZeTCt%2FYL84UEsuGXUO3fFhuTL8kG4Tzg%3D%3D";
    public static final int rows = 500;
    public static ArrayList<PerformanceInfo> tempPerformanceInfos = new ArrayList<>();
    public static ArrayList<PerformanceInfo> performanceInfos = new ArrayList<>();
    public static ArrayList<String> title = new ArrayList<>();
    public static ArrayList<String> startDate = new ArrayList<>();
    public static ArrayList<String> endDate = new ArrayList<>();
    public static String place;
    public static String contents1;
    public static ArrayList<String> genres = new ArrayList<>();
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

    public static ArrayList<String> getGenres(){
        return genres;
    }

    public static void getAllData(){
        SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String sDate = sm.format(cal.getTime());
        cal.add(Calendar.YEAR, 1);
        String eDate = sm.format(cal.getTime());
        System.out.println(sDate + " ////// " + eDate);
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
        sortData();
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
                        if(parser.getName().equals("contents1")){
                            inContents1 = true;
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
                        if(inContents1){
                            contents1 = parser.getText();
                            inContents1 = false;
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
        performanceDetailInfo = new PerformanceDetailInfo(detailPrice, detailUrl, place, detailAddr, contents1);
    }

    public static void setData(){
        for(int i=0; i<rows; i++){
            try{
                PerformanceInfo performanceInfo = new PerformanceInfo(seqNum.get(i), title.get(i), startDate.get(i), endDate.get(i),
                        realmName.get(i), area.get(i), thumbNail.get(i),
                        gpsX.get(i), gpsY.get(i));
                tempPerformanceInfos.add(performanceInfo);
            }
            catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }

    public static void sortData(){
        for(int i=0; i<tempPerformanceInfos.size(); i++){
            if(!performanceInfos.contains(tempPerformanceInfos.get(i))){
                performanceInfos.add(tempPerformanceInfos.get(i));
            }
            if(!genres.contains(tempPerformanceInfos.get(i).getRealmName())){
                genres.add(tempPerformanceInfos.get(i).getRealmName());
                System.out.println(tempPerformanceInfos.get(i).getRealmName());
            }
        }
        Collections.sort(genres);
        Collections.sort(performanceInfos, new Comparator<PerformanceInfo>() {
            @Override
            public int compare(PerformanceInfo o1, PerformanceInfo o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }
}