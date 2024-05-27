package com.example.allsails;

public class CartItem {

    String shopUrl;
    String newPrice;
    String oldPrice;
    String name;
    String imgUrl;
    String date;

    public CartItem(String shopUrl, String newPrice, String oldPrice, String name, String imgUrl, String date){
        this.shopUrl = shopUrl;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
        this.name = name;
        this.imgUrl = imgUrl;
        this.date = date;
    }

}
