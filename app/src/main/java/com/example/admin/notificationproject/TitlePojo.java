package com.example.admin.notificationproject;

/**
 * Created by Admin on 8/30/2017.
 */

public class TitlePojo {
    private String title, date, message;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TitlePojo() {
    }

    public TitlePojo(String title, String date, String message) {
        this.title = title;
        this.date = date;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



