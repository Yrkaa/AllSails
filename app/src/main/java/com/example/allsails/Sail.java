package com.example.allsails;

public class Sail {
    String imgUrl;
    String name;
    String oldPrice, newPrice;

    public Sail(String imgUrl, String name,  String oldPrice, String newPrice){
        this.imgUrl = imgUrl;
        this.name = name;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
}
