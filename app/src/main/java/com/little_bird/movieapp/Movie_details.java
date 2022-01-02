package com.little_bird.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Movie_details extends AppCompatActivity {

    ImageView imgMovie;
    TextView txtMovie;
    Button btnStart;
    String movieName,movieID,movieFile,movieImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imgMovie = findViewById(R.id.imageMovie);
        txtMovie = findViewById(R.id.txtMovie);
        btnStart = findViewById(R.id.btnStart);

        movieName = getIntent().getStringExtra("movieName");
        movieID = getIntent().getStringExtra("movieid");
        movieFile = getIntent().getStringExtra("movieFile");
        movieImg = getIntent().getStringExtra("movieImg");


        Glide.with(this).load(movieImg).into(imgMovie);

        txtMovie.setText(movieName);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity( new Intent(getApplicationContext(),Video_Play.class));
            }
        });
    }
}