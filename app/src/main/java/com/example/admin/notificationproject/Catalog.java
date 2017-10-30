package com.example.admin.notificationproject;

import java.io.Serializable;

/**
 * Created by Admin on 9/7/2017.
 */

public class Catalog implements Serializable {
    String catalogId;
    String catalogtitle;
    String catalogimageurl;


    String description,capacity, sizeAndWieght, camera, diplay, color1, color2, color3, color4, color5,title,image;

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

    public String getAssetTitle() {
        return assetTitle;
    }

    public void setAssetTitle(String assetTitle) {
        this.assetTitle = assetTitle;
    }

    String assetTitle;

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public void setCatalogtitle(String catalogtitle) {
        this.catalogtitle = catalogtitle;
    }

    //no argument constructor
    public Catalog() {

    }

    public void setCatalogimageurl(String catalogimageurl) {
        this.catalogimageurl = catalogimageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSizeAndWieght() {
        return sizeAndWieght;
    }

    public void setSizeAndWieght(String sizeAndWieght) {
        this.sizeAndWieght = sizeAndWieght;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getDiplay() {
        return diplay;
    }

    public void setDiplay(String diplay) {
        this.diplay = diplay;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getColor4() {
        return color4;
    }

    public void setColor4(String color4) {
        this.color4 = color4;
    }

    public String getColor5() {
        return color5;
    }

    public void setColor5(String color5) {
        this.color5 = color5;
    }

    public Catalog(String catalogId,  String catalogimageurl) {

        this.catalogId = catalogId;
        this.catalogtitle = catalogtitle;
        this.catalogimageurl = catalogimageurl;
    }


    public String getCatalogtitle() {
        return catalogtitle;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public String getCatalogimageurl() {
        return catalogimageurl;
    }
}

