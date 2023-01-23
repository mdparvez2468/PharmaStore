package com.example.pharmastore.models;

public class Cart {
    private String id;
    private int price;
    private int count;
    private String name;
    private String image;

    public Cart() {
    }

    public Cart(String id, int price, int count, String name, String image) {
        this.id = id;
        this.price = price;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
