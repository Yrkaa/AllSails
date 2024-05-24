package com.example.allsails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    //Массив товаров в корзине
    ArrayList<CartItem> cartData = new ArrayList<>();

    //БД
    SQLiteDatabase db;

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

        //Инициализация бд
        db = getBaseContext().openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        //Назначение кастомного шрифта
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/main.ttf");
        name.setTypeface(font);

        //Заполненение массива товаров
        Cursor c = db.rawQuery("SELECT * FROM Cart", null);
        while(c.moveToNext()){
            cartData.add(new CartItem(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
        }

        //Заполнение списка товаров
        CartListAdapter adapter = new CartListAdapter(this, cartData);
        cartItemsList.setAdapter(adapter);
    }
}