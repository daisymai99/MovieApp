package com.little_bird.movieapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.little_bird.movieapp.R;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    Context context;
    List<AllCategory> mlistCategory;

    public MainRecyclerAdapter(Context context, List<AllCategory> mlistCategory) {
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
        holder.textView.setText(mlistCategory.get(position).getCategoryTitle());
        setItemRecycler(holder.recyclerMain,mlistCategory.get(position).getmItemList());
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

    private void setItemRecycler(RecyclerView recycler,List<CategoryItem> mListItem){
        ItemRecyclerAdapter itemAdapter = new ItemRecyclerAdapter(context,mListItem);
        recycler.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recycler.setAdapter(itemAdapter);
    }
}
