package com.example.androidlab6;

public class Item {
    private int id;
    private String name;
    private int price;
    private int count;
    private String description;

    public Item(int id, String name, int price, int count, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
