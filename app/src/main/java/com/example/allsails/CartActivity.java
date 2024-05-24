package com.example.allsails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    //Массив товаров в корзине
    ArrayList<CartItem> cartData = new ArrayList<>();

    //Эл. разметки
    TextView name;
    RecyclerView cartItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Инициализация эл. разметки
        name = findViewById(R.id.cart_name_tv);
        cartItemsList = findViewById(R.id.cart_items_rv);

        //Назначение кастомного шрифта
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/main.ttf");
        name.setTypeface(font);

        //Заполненение массива товаров
        cartData.add(new CartItem("https://www.x5.ru/wp-content/uploads/2022/09/5ka_logo_rgb_02-e1663673744463-1024x406.png", "500", "1000", "ЯиЧкИ", "https://cojo.ru/wp-content/uploads/2022/12/perekrestok-1.webp"));

        //Заполнение списка товаров
        CartListAdapter adapter = new CartListAdapter(this, cartData);
        cartItemsList.setAdapter(adapter);
    }
}