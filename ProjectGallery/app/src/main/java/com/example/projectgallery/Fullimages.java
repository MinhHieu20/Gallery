package com.example.projectgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.share:
                BitmapDrawable drawable = (BitmapDrawable)fullimage.getDrawable();
                Bitmap bitmap =drawable.getBitmap();

                String bitMapPath = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"title",null);
                Uri uri = Uri.parse(bitMapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.putExtra(intent.EXTRA_TEXT,"Playstore Link: https//play.google.com/store/apps/details?id"+getPackageName());
                startActivity(Intent.createChooser(intent,"share"));

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}