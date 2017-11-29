package com.example.admin.notificationproject;

/**
 * Created by Admin on 10/2/2017.
 */

public class UserItemPojo {

    private String imageUri,name,deviceId,itemDate,itemTime,returnDate,color,bookingStatus,typeDevice,historyId,reasonForBooking, typeOs;
    private boolean itemReturn;

    public String getTypeOs() {
        return typeOs;
    }

    public void setTypeOs(String typeOs) {
        this.typeOs = typeOs;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getReasonForBooking() {
        return reasonForBooking;
    }

    public void setReasonForBooking(String reasonForBooking) {
        this.reasonForBooking = reasonForBooking;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public boolean getItemReturn() {
        return itemReturn;
    }

    public void setItemReturn(boolean itemReturn) {
        this.itemReturn = itemReturn;
    }

    public String getTypeDevice() {
        return typeDevice;
    }

    public void setTypeDevice(String typeDevice) {
        this.typeDevice = typeDevice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public UserItemPojo() {
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

