package com.example.allsails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    //Общая цена товаров
    float allPrice = 0;

    //Массив товаров в корзине
    ArrayList<CartItem> cartData = new ArrayList<>();

    //БД
    SQLiteDatabase db;

    //Эл. разметки
    TextView name, allPriceTv;
    RecyclerView cartItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Инициализация эл. разметки
        name = findViewById(R.id.cart_name_tv);
        cartItemsList = findViewById(R.id.cart_items_rv);
        allPriceTv = findViewById(R.id.cart_all_price_tv);

        //Инициализация бд
        db = getBaseContext().openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        //Назначение кастомного шрифта
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/main.ttf");
        name.setTypeface(font);

        //Заполненение массива товаров
        Cursor c = db.rawQuery("SELECT * FROM Cart", null);
        while(c.moveToNext()){
            cartData.add(new CartItem(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
        }

        //Подсчет общей цены
        for(CartItem e: cartData){
            if(e.shopUrl.equals("https://www.x5.ru/wp-content/uploads/2022/09/5ka_logo_rgb_02-e1663673744463-1024x406.png"))
                allPrice+=Float.parseFloat(e.newPrice.substring(3, e.newPrice.length()));
            else
                allPrice+=Float.parseFloat(e.newPrice.replace(",", "."));
        }
        allPriceTv.setText(allPriceTv.getText().toString()+" "+String.format("%.2f", allPrice).replace(".", ","));

        //Заполнение списка товаров
        CartListAdapter adapter = new CartListAdapter(this, cartData);
        cartItemsList.setAdapter(adapter);

        //Обучение
        SharedPreferences preferences = this.getSharedPreferences("com.example.allsails", MODE_PRIVATE);
        if(preferences.getBoolean("firstXDialog", true)){
            introduceDialog();
            preferences.edit().putBoolean("firstXDialog", false).apply();
        }
    }

    private void introduceDialog(){
        //Создание диалога
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.introduce_layout);
        dialog.show();

        //Инициализация эл. разметки
        TextView t = dialog.findViewById(R.id.introduce_text_tv);
        Button ok = dialog.findViewById(R.id.introduce_ok_btn);

        //Назначение шрифта
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/main.ttf");
        t.setTypeface(font);
        ok.setTypeface(font);

        //Работа с эл. разметки
        t.setText("Это ваша корзина. Тут собраны скидки, которые вы добавили к себе на странице приложения. Вы можете удалить какой-нибудь товар из корзины, задержав палец на нём. \nТакже снизу страницы написана общая цена всех товаров в корзине.");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}