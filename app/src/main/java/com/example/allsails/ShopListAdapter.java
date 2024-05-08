package com.example.allsails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    LayoutInflater inflater;

    //Массив с информацией о магазине
    List<Shop> data;

    Context c;

    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Создание страницы эл.
        View v = inflater.inflate(R.layout.shop_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        //Объект магазина с информацией
        Shop obj = data.get(position);

        //Название магазина
        holder.name.setText(obj.name);
        Picasso.get().load(obj.logoUrl).into(holder.logo);

        //Переход на страницу с информацией
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toShop = new Intent(c, ShopActivity.class);
                toShop.putExtra("logoUrl", obj.logoUrl);
                toShop.putExtra("name", obj.name);
                toShop.putExtra("id", obj.shopId);
                ((MainActivity) c).startActivity(toShop);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Кол-во эл. в списке
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView name;
        View v;

        public ViewHolder(View v){
            super(v);
            logo = v.findViewById(R.id.shop_item_logo_iv);
            name = v.findViewById(R.id.shop_item_name_tv);
            this.v = v;
        }
    }

    public ShopListAdapter(Context c, List<Shop> data){
        this.inflater = LayoutInflater.from(c);
        this.data = data;
        this.c = c;
    }
}
