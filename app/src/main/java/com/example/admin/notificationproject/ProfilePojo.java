package com.example.admin.notificationproject;

import java.io.Serializable;

/**
 * Created by Admin on 9/26/2017.
 */

public class ProfilePojo implements Serializable {
    private  String name,stuffNo,departmentName,email;
    private String id,logId;
    private  String idCreateed;
    private String image,secondImage;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }

    public String getIdCreateed() {
        return idCreateed;
    }

    public void setIdCreateed(String idCreateed) {
        this.idCreateed = idCreateed;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProfilePojo() {
    }

    public ProfilePojo(String name, String stuffNo, String departmentName, String email) {
        this.name = name;
        this.stuffNo = stuffNo;
        this.departmentName = departmentName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStuffNo() {
        return stuffNo;
    }

    public void setStuffNo(String stuffNo) {
        this.stuffNo = stuffNo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
