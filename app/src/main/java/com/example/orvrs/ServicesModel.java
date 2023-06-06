package com.example.orvrs;

public class ServicesModel {

    String purl;
    String name;
    String price;
    String desc;

    public ServicesModel() {
    }

    public ServicesModel(String purl, String name, String price, String desc) {
        this.purl = purl;
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
