package com.example.allsails;

import android.content.Context;
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
    List<Shop> data;

    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.shop_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        Shop obj = data.get(position);
        holder.name.setText(obj.name);
        Picasso.get().load(obj.logoUrl).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView name;

        public ViewHolder(View v){
            super(v);
            logo = v.findViewById(R.id.shop_item_logo_iv);
            name = v.findViewById(R.id.shop_item_name_tv);
        }
    }

    public ShopListAdapter(Context c, List<Shop> data){
        this.inflater = LayoutInflater.from(c);
        this.data = data;
    }
}
