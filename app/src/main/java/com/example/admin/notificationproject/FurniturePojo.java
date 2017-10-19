package com.example.admin.notificationproject;

import java.io.Serializable;

/**
 * Created by Admin on 12-Oct-17.
 */

public class FurniturePojo implements Serializable {
    private String id,serialNo,image,title,totalQuantity,duration,quantity,deliverance,type;

    public FurniturePojo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeliverance() {
        return deliverance;
    }

    public void setDeliverance(String deliverance) {
        this.deliverance = deliverance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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



    public String getTotalQuantity() {
        return totalQuantity;
    }




}
