package com.zebrunner.carina.automationexercise.gui.components;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductInfo {
    private String name;
    private String price;
    private static final Pattern PRICE_PATTERN = Pattern.compile("Rs\\. (\\d+(\\.\\d+)?)");

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


    public String getCurrency() {
        return price.split(" ")[0];
    }

    @Override
    public String toString() {
        return "ProductInfo{name='" + name + "', price='" + price + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}