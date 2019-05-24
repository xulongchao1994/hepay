package com.hechuang.hepay.bean;

public class BaiduListBean {
    private String uid;
    private String name;
    private String address;
    private double lat;
    private double lon;

    public BaiduListBean(String uid,String name, String address, double lat, double lon) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
