package com.example.allsails;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<Sail> pyaterochkaParser() throws IOException {
        ArrayList<Sail> sails = new ArrayList<>();

        Document doc = Jsoup.connect("https://5ka.ru/special_offers/").get();

        ArrayList<String> img = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> old = new ArrayList<>();
        ArrayList<String> newP = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();

        Elements x1 = doc.getElementsByClass("price-discount");
        for(Element e: x1){
            newP.add(e.child(0).text()+"."+e.child(1).child(0).text());
            String str = e.child(1).child(1).text();
            old.add(str.split(" ")[0]+str.split(" ")[1]);
        }

        Elements x2 = doc.getElementsByClass("image-cont");
        for(Element e: x2){
            img.add(e.child(0).attr("src"));
            name.add(e.child(0).attr("alt"));
        }

        Elements x3 = doc.getElementsByClass("item-date");
        for(Element e: x3){
            date.add(e.text());
        }

        for(int i = 0; i < img.size(); i++){
            sails.add(new Sail(img.get(i), name.get(i), old.get(i), newP.get(i), date.get(i)));
        }

        return sails;

    }

    public static ArrayList<Sail> perekrestokParser() throws IOException {

        ArrayList<Sail> sails = new ArrayList<>();

        for(int i = 1; i <=1; i++){

            ArrayList<String> img = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> old = new ArrayList<>();
            ArrayList<String> newP = new ArrayList<>();

            Document doc = Jsoup.connect("https://www.perekrestok.ru/cat/d?append=1&page="+i).get();

            Elements x1 = doc.getElementsByClass("product-card__link");
            for(Element e: x1){
                name.add(e.child(0).text());

                Document card = Jsoup.connect("https://www.perekrestok.ru"+e.attr("href")).get();

                Elements cardImg = card.getElementsByClass("iiz__img   ");
                for(Element cardE: cardImg){
                    img.add(cardE.attr("src"));
                    break;
                }

                Elements prices = card.selectXpath("//*[@id=\"price-card\"]/div[1]/div[1]/div[1]");
                for(Element cardE: prices){
                    String newPrice = cardE.child(0).text();
                    newP.add(newPrice.substring(4, newPrice.length()-2));
                    try{
                        String oldPrice = cardE.child(1).child(0).text();
                        old.add(oldPrice.substring(11, oldPrice.length()-2));
                    } catch (IndexOutOfBoundsException err){
                        old.add("Цена не указана");
                    }
                    break;
                }

            }

            for(int j = 0; j < img.size(); j++){
                sails.add(new Sail(img.get(j), name.get(j), old.get(j), newP.get(j), ""));
            }

        }

        return sails;

    }

}
