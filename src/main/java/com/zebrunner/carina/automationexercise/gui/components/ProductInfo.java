package com.zebrunner.carina.automationexercise.gui.components;


public class ProductInfo {
    private String name;
    private String price;

    public ProductInfo(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductInfo{name='" + name + "', price='" + price + "'}";
    }
}
