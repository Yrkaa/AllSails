package com.example.allsails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //БД
    SQLiteDatabase db;

    //Адаптер для списка магазинов
    ShopListAdapter adapter;

    //Множество всех магазинов
    ArrayList<Shop> shopData = new ArrayList<>();

    //Эл. разметки
    RecyclerView shopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализация эл. разметки
        shopList = findViewById(R.id.shops_rv);

        //Инициализация бд
        db = getBaseContext().openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        //Заполнение бд, если она пустая
        SharedPreferences preferences = this.getSharedPreferences("com.example.allsails", MODE_PRIVATE);
        if(preferences.getBoolean("first", true)){
            db.execSQL("CREATE TABLE  IF NOT EXISTS Shops(_id INTEGER PRIMARY KEY AUTOINCREMENT, logo_url TEXT, name TEXT) ");
            db.execSQL("INSERT INTO Shops(logo_url, name) VALUES ('https://www.x5.ru/wp-content/uploads/2022/09/5ka_logo_rgb_02-e1663673744463-1024x406.png', 'Пятёрочка')");
            db.execSQL("INSERT INTO Shops(logo_url, name) VALUES ('https://dixy.ru/images/logo.png', 'Дикси')");
            preferences.edit().putBoolean("first", false).apply();
        }

        //Заполнение множества всех магазинов
        Cursor cursor = db.rawQuery("SELECT * FROM Shops", null);
        while (cursor.moveToNext()){
            shopData.add(new Shop(cursor.getString(1), cursor.getString(2), cursor.getInt(0)));
        }

        //Заполнение списка магазинов
        adapter = new ShopListAdapter(MainActivity.this, shopData);
        shopList.setAdapter(adapter);

    }
}