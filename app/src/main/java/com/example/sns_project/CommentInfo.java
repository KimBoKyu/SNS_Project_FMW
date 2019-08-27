package com.example.sns_project;
import java.util.Date;

public class CommentInfo {
    String comment;
    Date date;
    String user;

    public CommentInfo(String comment, Date date, String user) {
        this.comment = comment;
        this.date = date;
        this.user = user;
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
}
