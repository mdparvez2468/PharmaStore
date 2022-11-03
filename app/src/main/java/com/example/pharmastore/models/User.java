package com.example.pharmastore.models;

public class User {

    private String phone;
    private String name;
    private String image;

    public User() {
    }

    public User(String phone, String name, String image) {
        this.phone = phone;
        this.name = name;
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
}
