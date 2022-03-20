package com.example.projectgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Fullimages extends AppCompatActivity {

    private ImageView fullimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimages);

        fullimage = findViewById(R.id.fullimage);
        Glide.with(this).load(getIntent().getStringExtra("image")).into(fullimage);
    }
}