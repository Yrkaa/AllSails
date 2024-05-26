package com.example.allsails;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SailListAdapter extends RecyclerView.Adapter<SailListAdapter.ViewHolder> {

    LayoutInflater inflater;

    Context c;

    //Массив всех скидок
    List<Sail> data;

    //Адрес на лого магаза
    String shopUrl;

    @NonNull
    @Override
    public SailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Получение страницы элемента
        View v = inflater.inflate(R.layout.sail_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SailListAdapter.ViewHolder holder, int position) {
        //Объект скидки с информацией
        Sail obj = data.get(position);

        //Название товара
        holder.name.setText(obj.name);

        //Специальный шрифт
        Typeface font = Typeface.createFromAsset(c.getAssets(), "fonts/main.ttf");
        holder.name.setTypeface(font);
        holder.oldPrice.setTypeface(font);
        holder.newPrice.setTypeface(font);

        //Старая цена
        holder.oldPrice.setText(obj.oldPrice+",");
        holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Новая цена
        holder.newPrice.setText(obj.newPrice+" р.");

        //Картинка товара
        Picasso.get().load(obj.imgUrl).into(holder.img);

        //Добавление товара в корзину
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = c.openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("SELECT * FROM Cart", null);
                boolean first = true;
                while(cursor.moveToNext()){
                    if(cursor.getString(4).equals(obj.name)){
                        Toast.makeText(c, "Уже есть в корзине", Toast.LENGTH_SHORT).show();
                        first = false;
                        break;
                    }
                }

                cursor.close();

                if(first){
                    db.execSQL("INSERT INTO Cart(shopUrl, newPrice, oldPrice, name, imgUrl) VALUES ('"+ shopUrl+"', '"+obj.newPrice+"', '"+obj.oldPrice+"', '"+obj.name+"', '"+obj.imgUrl+"')");
                    Toast.makeText((Context) c, "Добавлено в корзину", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        //Количество эл. в списке
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, oldPrice, newPrice;
        View v;

        public ViewHolder(View v){
            super(v);
            img = v.findViewById(R.id.sail_img_iv);
            name = v.findViewById(R.id.sail_name_tv);
            oldPrice = v.findViewById(R.id.sail_old_price_tv);
            newPrice = v.findViewById(R.id.sail_new_price_tv);
            this.v = v;
        }
    }

    public SailListAdapter(Context c, List<Sail> data, String shopUrl){
        this.inflater = LayoutInflater.from(c);
        this.data = data;
        this.c = c;
        this.shopUrl = shopUrl;
    }
}
