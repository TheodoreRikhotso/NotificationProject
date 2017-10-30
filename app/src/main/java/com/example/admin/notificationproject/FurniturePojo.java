package com.example.admin.notificationproject;

import java.io.Serializable;

/**
 * Created by Admin on 12-Oct-17.
 */

public class FurniturePojo implements Serializable {
    private String id;
    private String serialNo;
    private String title;
    private String totalQuantity;
    private String duration;
    private String quantity;
    private String deliverance;
    private String type ,image1,image2;


    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public FurniturePojo() {

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
