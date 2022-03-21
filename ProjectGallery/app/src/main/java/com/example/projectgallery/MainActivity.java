package com.example.projectgallery;

import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    Button albumbtn;
    Button timelinebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumbtn = findViewById(R.id.albumbtn);
        timelinebtn = findViewById(R.id.timelinebtn);

        albumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.album);
            }
        });
    }

    private  static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSIONS_COUNT = 2;

    @SuppressLint("NewApi")
    private boolean arePermissionsDenied () {
        for(int i = 0 ; i < PERMISSIONS_COUNT; i++) {
            if(checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissioms,
                                           final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissioms,grantResults);
        if(requestCode == REQUEST_PERMISSIONS && grantResults.length>0) {
            if(arePermissionsDenied()) {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE)))
                        .clearApplicationUserData();
                recreate();
            }
        }
    }

    private boolean isGalleryInitialized;

    @Override
    protected  void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }
        if(!isGalleryInitialized) {
            final GridView gridView = findViewById(R.id.gridView);
            final GalleryAdapter galleryAdapter = new GalleryAdapter();
            final File imagesDir = new File(String.valueOf(Environment.
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
            final File[] files = imagesDir.listFiles();
            final int filesCount = files.length;
            final List<String > filesList = new ArrayList<>();
            for(int i = 0; i < filesCount; i ++) {
                final String path = files[i].getAbsolutePath();
                if(path.endsWith(".jpg") || path.endsWith(".png")) {
                    filesList.add(path);
                }
            }
            galleryAdapter.setData(filesList);
            gridView.setAdapter(galleryAdapter);
            isGalleryInitialized = true;
        }
    }


    final  class GalleryAdapter extends BaseAdapter {

        private List<String> data = new ArrayList<>();

        void setData(List<String> data) {
            if(this.data.size() > 0) {
                data.clear();
            }
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageView imageView;
            if(convertView == null) {
                imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                        parent,false);
            }
            else {
                imageView = (ImageView) convertView;
            }
            Glide.with(MainActivity.this).load(data.get(position)).centerCrop().into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,Fullimages.class);
                    intent.putExtra("image",data.get(position));
                    MainActivity.this.startActivity(intent);

                }
            });

            return imageView;
        }
    }

}