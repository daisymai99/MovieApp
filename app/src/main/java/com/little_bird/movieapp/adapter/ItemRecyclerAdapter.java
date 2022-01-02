package com.little_bird.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.little_bird.movieapp.Movie_details;
import com.little_bird.movieapp.R;
import com.little_bird.movieapp.model.CategoryItem;
import com.little_bird.movieapp.model.Result;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {
    Context context;
    List<Result> categoryItemList ;

    public ItemRecyclerAdapter(Context context, List<Result> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String poster = "https://image.tmdb.org/t/p/w500" + categoryItemList.get(position).getPosterPath();


        Glide.with(context).load(poster).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Movie_details.class);
                i.putExtra("movieid",categoryItemList.get(position).getId());
                i.putExtra("movieName",categoryItemList.get(position).getTitle());
                i.putExtra("movieImg",poster);
                i.putExtra("movieFile",categoryItemList.get(position).getVideo());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

       ImageView img;

        public ItemViewHolder(@NonNull View itemView) {

            super(itemView);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}
