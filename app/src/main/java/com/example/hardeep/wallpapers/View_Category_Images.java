package com.example.hardeep.wallpapers;

import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class View_Category_Images extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    int pos=0;
    String category;
    boolean alreadyExecuted=false;
    GridLayoutManager manager;

    FirebaseRecyclerAdapter<image_details,ViewImagesViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__category__images);

        category=getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(category);


        manager=new GridLayoutManager(this,2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.viewimagesrecyclerview);
        recyclerView.setLayoutManager(manager);

        progressBar=findViewById(R.id.viewimagespbar);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("CatImages").child(category);
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter=new FirebaseRecyclerAdapter<image_details, ViewImagesViewHolder>
                (image_details.class,R.layout.imageformat,ViewImagesViewHolder.class,databaseReference) {

            @Override
            public void onViewAttachedToWindow(@NonNull ViewImagesViewHolder holder) {
                super.onViewAttachedToWindow(holder);
                if(!alreadyExecuted) {
                    recyclerView.smoothScrollToPosition(pos);
                    alreadyExecuted = true;
                }

            }

            @Override
            protected void populateViewHolder(ViewImagesViewHolder viewHolder, image_details model, final int position) {
                viewHolder.setImage(getApplicationContext(),model.getImage());
                progressBar.setVisibility(View.GONE);


                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String url=getItem(position).getImage();
                        Log.i("pos",Integer.toString(position));
                        pos=position;
                        Intent intent=new Intent(getApplicationContext(),Image_Download.class);
                        intent.putExtra("url",url);
                        intent.putExtra("pos",position);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("po",Integer.toString(pos));
        alreadyExecuted=false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out, R.anim.right_in);
    }
}
