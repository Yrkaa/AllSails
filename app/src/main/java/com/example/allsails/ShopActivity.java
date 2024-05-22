package com.example.allsails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    //Эл. разметки
    ImageView shopLogo;
    RecyclerView sailList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Инициализация эл. разметки
        shopLogo = findViewById(R.id.shop_logo_iv);
        sailList = findViewById(R.id.sail_rv);
        progressBar = findViewById(R.id.progress_bar);

        //Назначение картинки магазина
        Picasso.get().load(getIntent().getStringExtra("logoUrl")).into(shopLogo);

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
                        SailListAdapter adapter = new SailListAdapter(ShopActivity.this, finalSails);
                        sailList.setAdapter(adapter);

                        //Скрыть строку загрузки
                        progressBar.setVisibility(View.INVISIBLE);

                }
            });
        }
    });
}