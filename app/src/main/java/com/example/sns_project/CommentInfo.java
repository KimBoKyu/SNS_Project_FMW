package com.example.sns_project;
import java.util.Date;

public class CommentInfo {
    String comment;
    Date date;
    String user;
    String photoUrl;
    String id;

    public CommentInfo(String comment, Date date, String user, String photoUrl, String id) {
        this.comment = comment;
        this.date = date;
        this.user = user;
        this.photoUrl = photoUrl;
        this.id = id;
    }
    public CommentInfo(String comment, Date date, String user, String id){
        this.comment = comment;
        this.date = date;
        this.user = user;
        this.photoUrl = null;
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhotoUrl(){return photoUrl;}

    public void setPhotoUrl(String photoUrl){ this.photoUrl = photoUrl;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
