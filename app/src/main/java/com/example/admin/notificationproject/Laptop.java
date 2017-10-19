package com.example.admin.notificationproject;

import java.io.Serializable;

/**
 * Created by Admin on 12-Oct-17.
 */

public class Laptop implements Serializable {
    private String id,title,image,serialNo,totalQuantity,duration,storage,os,graphics,ram;

    public Laptop() {

    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }



    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }
    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

}
