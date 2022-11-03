package com.example.pharmastore.models;

public class Product {
    private String id;
    private int price;
    private int mrp;
    private String name;
    private String image;

    public Product() {
    }

    public Product(String id, int price, int mrp, String name, String image) {
        this.id = id;
        this.price = price;
        this.mrp = mrp;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
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
