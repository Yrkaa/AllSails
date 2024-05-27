package com.example.allsails;

public class Sail {
    String imgUrl;
    String name;
    String oldPrice, newPrice;
    String date;

    public Sail(String imgUrl, String name,  String oldPrice, String newPrice, String date){
        this.imgUrl = imgUrl;
        this.name = name;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.date = date;
    }
}
