package com.example.allsails;

public class Sail {
    String imgUrl;
    String name;
    int oldPrice, newPrice;

    public Sail(String imgUrl, String name,  int oldPrice, int newPrice){
        this.imgUrl = imgUrl;
        this.name = name;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
}
