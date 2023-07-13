package com.example.wagba.model;

import com.example.wagba.Meals;

import java.util.ArrayList;

public class OrdersModel {
    private String key,name,image,price,gate, address, state;
    private ArrayList<Meals> meals;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGate() {
        return gate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OrdersModel(String key, String price, String gate, String address, String state, ArrayList<Meals> meals, String date) {
        this.key = key;
        this.price = price;
        this.gate = gate;
        this.address = address;
        this.state = state;
        this.meals = meals;
        this.date=date;
    }
    public OrdersModel(String key, String name, String image, String price, String gate, String address, String state, ArrayList<Meals> meals) {
        this.key = key;
        this.name = name;
        this.image = image;
        this.price = price;
        this.gate = gate;
        this.address = address;
        this.state = state;
        this.meals = meals;
    }

    public OrdersModel(String key, String name, String image, String price, String gate, String address, ArrayList<Meals> meals) {
        this.key = key;
        this.name = name;
        this.image = image;
        this.price = price;
        this.gate = gate;
        this.address = address;
        this.meals = meals;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Meals> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meals> meals) {
        this.meals=meals;
    }

    public OrdersModel() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
