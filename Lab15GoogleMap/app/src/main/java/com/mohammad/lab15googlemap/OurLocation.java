package com.mohammad.lab15googlemap;

import java.util.Date;

/**
 * Created by user on 11/14/2015.
 */
public class OurLocation {
    private String lat;
    private String lng;
    private String address;
    private Date date;







    //make object
    public OurLocation(String lat, String lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.date = new Date();
    }


    //get from database
    public OurLocation(String lat, String lng, String address,Date date) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.date = date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}