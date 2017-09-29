package com.example.admin.notificationproject;

/**
 * Created by Admin on 9/22/2017.
 */

public class ImageUpload {
    public String name;
    public String url;
    public String description;
    public String descriptions;
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ImageUpload(String name, String url, String description , String descriptions) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.description = descriptions;
    }

    public String getDescription() {
        return description;

    }

    public ImageUpload(){}

    public String getDescriptions() {
        return descriptions;
    }
}
