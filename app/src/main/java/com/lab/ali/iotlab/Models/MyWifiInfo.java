package com.lab.ali.iotlab.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MyWifiInfo {
    @SerializedName("date")
    private Date date;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longtitude")
    private double longtitude;
    @SerializedName("ssid")
    private String SSID;
    @SerializedName("rssi")
    private long rssi;


    public MyWifiInfo(){}

    public static MyWifiInfo getFromJson(JsonObject jsonObject){
        Gson gson  = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(jsonObject,MyWifiInfo.class);
    }

    public String toString(){
        Gson gson  = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(this);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public long getRssi() {
        return rssi;
    }

    public void setRssi(long rssi) {
        this.rssi = rssi;
    }
}
