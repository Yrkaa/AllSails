package com.example.allsails;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    //Список с информацией
    List<CartItem> data;

    LayoutInflater inflater;
    Context c;

    //Конструктор
    public CartListAdapter(Context c, List<CartItem> data){
        this.data = data;
        this.inflater = LayoutInflater.from(c);
        this.c = c;
    }

    //Кдасс для объекта списка
    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView shop, iv;
        TextView name, oldPrice, newPrice, date;
        View v;

        public ViewHolder(View v){
            super(v);
            shop = v.findViewById(R.id.cart_item_shop);
            iv = v.findViewById(R.id.cart_item_iv);
            name = v.findViewById(R.id.cart_item_name);
            oldPrice = v.findViewById(R.id.cart_item_old_price);
            newPrice = v.findViewById(R.id.cart_item_new_price);
            date = v.findViewById(R.id.cart_item_date);
            this.v = v;
        }

    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Создание объекта списка
        View v = inflater.inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        //Заполнение информации
        CartItem obj = data.get(position);
        holder.name.setText(obj.name);
        holder.oldPrice.setText(obj.oldPrice+",");
        holder.newPrice.setText(obj.newPrice+" р.");
        holder.date.setText(obj.date);
        Picasso.get().load(obj.imgUrl).into(holder.iv);
        Picasso.get().load(obj.shopUrl).into(holder.shop);

        //Перечеркнутый текст для старой цены
        holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Кастомный шрифт
        Typeface font = Typeface.createFromAsset(c.getAssets(), "fonts/main.ttf");
        holder.name.setTypeface(font);
        holder.oldPrice.setTypeface(font);
        holder.newPrice.setTypeface(font);
        holder.date.setTypeface(font);

        //Удаление эл. из корзины
        holder.v.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                notifyItemRemoved(position);
                data.remove(position);
                int sqlId = 0;
                SQLiteDatabase db = c.openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("SELECT * FROM Cart", null);
                while (cursor.moveToNext()){
                    if(sqlId == position){
                        db.execSQL("DELETE FROM Cart WHERE _id="+cursor.getInt(0));
                        break;
                    }
                    sqlId++;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Длина списка
        return data.size();
    }
}
