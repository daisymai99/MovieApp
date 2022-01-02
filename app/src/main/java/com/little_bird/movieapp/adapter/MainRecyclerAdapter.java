package com.little_bird.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.little_bird.movieapp.R;
import com.little_bird.movieapp.model.AllCategory;
import com.little_bird.movieapp.model.CategoryItem;
import com.little_bird.movieapp.model.ListOfMovie;
import com.little_bird.movieapp.model.MovieResults;
import com.little_bird.movieapp.model.Result;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    Context context;
    List<MovieResults> mlistCategory;

    public MainRecyclerAdapter(Context context, List<MovieResults> mlistCategory) {
        this.context = context;
        this.mlistCategory = mlistCategory;
    }

    @NonNull
    @Override
    public MainRecyclerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.MainViewHolder holder, int position) {
        holder.textView.setText(mlistCategory.get(position).getPaper().toString());
        setItemRecycler(holder.recyclerMain,mlistCategory.get(position).getResults());
    }

    @Override
    public int getItemCount() {
        return mlistCategory.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RecyclerView recyclerMain;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_main_recycler);
            recyclerMain = itemView.findViewById(R.id.item_recycler);
        }


    }

    private void setItemRecycler(RecyclerView recycler,List<Result> mListItem){
        ItemRecyclerAdapter itemAdapter = new ItemRecyclerAdapter(context,mListItem);
        recycler.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recycler.setAdapter(itemAdapter);
    }
}
