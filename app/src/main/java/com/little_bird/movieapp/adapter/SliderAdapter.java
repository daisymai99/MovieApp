package com.little_bird.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.little_bird.movieapp.Movie_details;
import com.little_bird.movieapp.R;
import com.little_bird.movieapp.model.Result;
import com.little_bird.movieapp.model.Slider;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    List<Result> mlistSlider;
    private Context context;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slider_item,null);

        ImageView img = slideLayout.findViewById(R.id.slider_img);
        TextView textView = slideLayout.findViewById(R.id.slider_title);
//        img.setImageResource(mlistSlider.get(position).getImage());

        String poster = "https://image.tmdb.org/t/p/w500" + mlistSlider.get(position).getPosterPath();

        Glide.with(context).load(poster).into(img);
        textView.setText(mlistSlider.get(position).getTitle());

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Movie_details.class);
                i.putExtra("movieid",mlistSlider.get(position).getId());
                i.putExtra("movieName",mlistSlider.get(position).getTitle());
                i.putExtra("movieImg",poster);
                i.putExtra("movieFile",mlistSlider.get(position).getVideo());
                context.startActivity(i);
            }
        });

        container.addView(slideLayout);
        return slideLayout;
    }

    public SliderAdapter(List<Result> mlistSlider, Context context) {
        this.mlistSlider = mlistSlider;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mlistSlider.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view==object;
    }


    // avoid app crash when slide end
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
