package com.example.hardeep.wallpapers;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class View_Local_Image extends AppCompatActivity {

    ImageView downloadedimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__local__image);

        downloadedimage=findViewById(R.id.localimage);


        String img=getIntent().getStringExtra("img");

        Picasso.get().load(Uri.parse(img)).into(downloadedimage);
    }
}
