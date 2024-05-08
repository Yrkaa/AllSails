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
            old.add(e.child(1).child(1).text());
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
}
