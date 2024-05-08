package com.example.allsails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    //Массив данных о скидках
    ArrayList<Sail> sailData = new ArrayList<>();

    //Эл. разметки
    ImageView shopLogo;
    RecyclerView sailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Инициализация эл. разметки
        shopLogo = findViewById(R.id.shop_logo_iv);
        sailList = findViewById(R.id.sail_rv);

        //Назначение картинки магазина
        Picasso.get().load(getIntent().getStringExtra("logoUrl")).into(shopLogo);

        //Заполнение массива
        sailData.add(new Sail("https://promo.5ka.ru/i/upload/main/%D0%A0%D0%9A%20%D0%BB%D1%83%D0%BA.jpg", "Чипсики ", 100, 90));
        sailData.add(new Sail("https://promo.5ka.ru/i/upload/main/%D0%A0%D0%9A%20%D0%BB%D1%83%D0%BA.jpg", "Чипсики ", 100, 90));
        sailData.add(new Sail("https://promo.5ka.ru/i/upload/main/%D0%A0%D0%9A%20%D0%BB%D1%83%D0%BA.jpg", "Чипсики ", 100, 90));

        //Заполнение списка
        SailListAdapter adapter = new SailListAdapter(this, sailData);
        sailList.setAdapter(adapter);
    }
}