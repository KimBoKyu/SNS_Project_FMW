package com.example.sns_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostInfo implements Serializable {
    private String title;
    private String performanceTitle;
    private ArrayList<String> contents;
    private ArrayList<String> formats;
    private String publisher;
    private Date createdAt;
    private String id;
    private float star;

    public PostInfo(String title, String performanceTitle, ArrayList<String> contents, ArrayList<String> formats, String publisher, Date createdAt, String id, float star){
        this.title = title;
        this.performanceTitle = performanceTitle;
        this.contents = contents;
        this.formats = formats;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
        this.star = star;
    }

    public PostInfo(String title, String performanceTitle, ArrayList<String> contents, ArrayList<String> formats, String publisher, Date createdAt, float star){
        this.title = title;
        this.performanceTitle = performanceTitle;
        this.contents = contents;
        this.formats = formats;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.star = star;
    }

    public PostInfo(String title, ArrayList<String> contents, ArrayList<String> formats, String publisher, Date createdAt){
        this.title = title;
        this.contents = contents;
        this.formats = formats;
        this.publisher = publisher;
        this.createdAt = createdAt;
    }

    public PostInfo(String title, ArrayList<String> contents, ArrayList<String> formats, String publisher, Date createdAt, String id){
        this.title = title;
        this.contents = contents;
        this.formats = formats;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
    }


    public Map<String, Object> getPostInfo(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("title",title);
        docData.put("performanceTitle", performanceTitle);
        docData.put("contents",contents);
        docData.put("formats",formats);
        docData.put("publisher",publisher);
        docData.put("createdAt",createdAt);
        docData.put("star", star);
        return  docData;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getPerformanceTitle() {return this.performanceTitle; }
    public void setPerformanceTitle(String performanceTitle){
        this.performanceTitle = performanceTitle;
    }
    public ArrayList<String> getContents(){
        return this.contents;
    }
    public void setContents(ArrayList<String> contents){
        this.contents = contents;
    }
    public ArrayList<String> getFormats(){
        return this.formats;
    }
    public void setFormats(ArrayList<String> formats){
        this.formats = formats;
    }
    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public Date getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
    public float getStar(){ return this.star; }
    public void setStar(float star){ this.star = star; }
}
