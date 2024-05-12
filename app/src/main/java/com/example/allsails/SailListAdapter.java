package com.example.allsails;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SailListAdapter extends RecyclerView.Adapter<SailListAdapter.ViewHolder> {

    LayoutInflater inflater;

    //Массив всех скидок
    List<Sail> data;

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

        //Старая цена
        holder.oldPrice.setText(obj.oldPrice+",");
        holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Новая цена
        holder.newPrice.setText(obj.newPrice+" р.");

        //Картинка товара
        Picasso.get().load(obj.imgUrl).into(holder.img);
    }

    @Override
    public int getItemCount() {
        //Количество эл. в списке
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, oldPrice, newPrice;

        public ViewHolder(View v){
            super(v);
            img = v.findViewById(R.id.sail_img_iv);
            name = v.findViewById(R.id.sail_name_tv);
            oldPrice = v.findViewById(R.id.sail_old_price_tv);
            newPrice = v.findViewById(R.id.sail_new_price_tv);
        }
    }

    public SailListAdapter(Context c, List<Sail> data){
        this.inflater = LayoutInflater.from(c);
        this.data = data;
    }
}
