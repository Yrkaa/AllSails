package com.example.allsails;

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

        for(int i = 0; i < img.size(); i++){
            sails.add(new Sail(img.get(i), name.get(i), old.get(i), newP.get(i)));
        }

        return sails;

    }

    public static ArrayList<Sail> diksiParser() throws IOException {
        ArrayList<Sail> sails = new ArrayList<>();

        for(int i = 1; i <=30; i++){
            Document doc = Jsoup.connect("https://dixy.ru/catalog/?PAGEN_1="+i).get();

            ArrayList<String> img = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> old = new ArrayList<>();
            ArrayList<String> newP = new ArrayList<>();

            Elements x1 = doc.getElementsByClass("dixyCatalogItemPrice__pricesset");
            for(Element e: x1){
                String str = e.child(0).child(1).text()+"."+e.child(1).text();
                newP.add(str.substring(0, str.length()-1));
            }

            Elements x2 = doc.getElementsByClass("dixyCatalogItem__picplacer");
            for(Element e: x2){
                img.add(e.child(0).attr("src"));
                name.add(e.child(0).attr("alt"));
            }

            Elements x3 = doc.getElementsByClass("dixyCatalogItemPrice__oldprice");
            for(Element e: x3){
                old.add(e.text());
            }

            for(int j = 0; j < img.size(); j++){
                sails.add(new Sail(img.get(j), name.get(j), old.get(j), newP.get(j)));
            }

        }

        return sails;

    }

    public static ArrayList<Sail> magnitParser() throws IOException {
        ArrayList<Sail> sails = new ArrayList<>();

        for(int i = 0; i <=2500; i+=36){
            Document doc = Jsoup.connect("https://magnit.ru/promo/?offset="+i).get();

            ArrayList<String> img = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> old = new ArrayList<>();
            ArrayList<String> newP = new ArrayList<>();

            Elements x1 = doc.getElementsByClass("new-card-product__price");
            for(Element e: x1){
                old.add(e.child(1).text());
                newP.add(e.child(0).text());
            }

            Elements x2 = doc.getElementsByClass("new-card-product__image-wrap new-card-product__image-wrap-catalog");
            for (Element e: x2){
                name.add(e.child(0).attr("alt"));
                img.add(e.child(0).attr("src"));
            }

            for(int j = 0; j < img.size(); j++){
                sails.add(new Sail(img.get(j), name.get(j), old.get(j), newP.get(j)));
            }

        }

        return sails;
    }

}
