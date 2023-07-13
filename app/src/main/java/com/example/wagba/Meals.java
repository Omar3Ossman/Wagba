package com.example.wagba;

public class Meals {
    String mealName;
    String mealPrice;
    String mealImage;
    int rating;

    public Meals(String mealName, String mealPrice, String mealImage) {
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealImage = mealImage;
    }
public Meals(){}
    public String getMealName() {
        return mealName;
    }

    public String getMealPrice() {
        return mealPrice;
    }

    public String getMealImage() {
        return mealImage;
    }

    public int getRating() {
        return rating;
    }

    public Meals(String mealName, String mealPrice, String mealImage, int rating) {
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealImage = mealImage;
        this.rating = rating;
    }





}
