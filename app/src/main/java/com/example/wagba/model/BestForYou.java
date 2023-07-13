package com.example.wagba.model;

public class BestForYou {
    String name;

    public String getResID() {
        return resID;
    }

    String resID;
    String imageURL;

    public BestForYou(String resID,String name, String imageURL) {
        this.name = name;
        this.resID=resID;
        this.imageURL = imageURL;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
