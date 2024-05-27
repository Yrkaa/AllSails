package com.example.allsails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    //Эл. разметки
    ImageView shopLogo;
    RecyclerView sailList;
    ProgressBar progressBar;

    //Адрес на лого магаза
    String shopUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Инициализация эл. разметки
        shopLogo = findViewById(R.id.shop_logo_iv);
        sailList = findViewById(R.id.sail_rv);
        progressBar = findViewById(R.id.progress_bar);

        //Назначение картинки магазина
        shopUrl = getIntent().getStringExtra("logoUrl");
        Picasso.get().load(shopUrl).into(shopLogo);

        //Обучение
        SharedPreferences preferences = this.getSharedPreferences("com.example.allsails", MODE_PRIVATE);
        if(preferences.getBoolean("firtShopDialog", true)){
            introduceDialog();
            preferences.edit().putBoolean("firtShopDialog", false).apply();
        }

        loadSails.start();
    }

    Thread loadSails = new Thread(new Runnable() {
        @Override
        public void run() {
            //Множество скидок
            ArrayList<Sail> sails = new ArrayList<>();

            //Id магазина
            int id = ShopActivity.this.getIntent().getIntExtra("id", 0);

            //Выбор парсера в зависимости от id
            try {
                switch (id){
                    case 1:
                        sails = Parser.pyaterochkaParser();
                        break;
                    case 2:
                        sails = Parser.perekrestokParser();
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Финальное множество скидок
            ArrayList<Sail> finalSails = sails;

            ShopActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        //Заполнение списка
                        SailListAdapter adapter = new SailListAdapter(ShopActivity.this, finalSails, shopUrl);
                        sailList.setAdapter(adapter);

                        //Скрыть строку загрузки
                        progressBar.setVisibility(View.INVISIBLE);

                }
            });
        }
    });

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
        t.setText("Это страница магазина. Тут собраны скидки из конкретного магазина. \nВы можете добавить любой товар к себе в корзину, кликнув на него");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}