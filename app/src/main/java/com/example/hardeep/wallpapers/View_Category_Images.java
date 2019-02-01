package com.example.hardeep.wallpapers;

import android.content.Intent;
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
    String category;
    private InterstitialAd interstitialAd;

    FirebaseRecyclerAdapter<image_details,ViewImagesViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__category__images);

        category=getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(category);


        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-9643831152040209/5697609074");
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("3F1257446C2A8696D45887ED25C4629F").build());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.viewimagesrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager( this,2));
        progressBar=findViewById(R.id.viewimagespbar);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("CatImages").child(category);
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter=new FirebaseRecyclerAdapter<image_details, ViewImagesViewHolder>
                (image_details.class,R.layout.imageformat,ViewImagesViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(ViewImagesViewHolder viewHolder, image_details model, final int position) {
                viewHolder.setImage(getApplicationContext(),model.getImage());
                progressBar.setVisibility(View.GONE);

                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(interstitialAd.isLoaded())
                        {
                            interstitialAd.show();
                        }
                        else Log.i("Main","Not Loaded");
                        String url=getItem(position).getImage();
                        Intent intent=new Intent(getApplicationContext(),Image_Download.class);
                        intent.putExtra("url",url);
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out, R.anim.right_in);
    }
}
