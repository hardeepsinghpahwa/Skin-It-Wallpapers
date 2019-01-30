package com.example.hardeep.wallpapers.View_Pager_Frags;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardeep.wallpapers.R;
import com.example.hardeep.wallpapers.View_Local_Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class Local_Images_Adapter extends RecyclerView.Adapter<LocalViewHolder> {

    ArrayList<Uri> images;
    Context c;

    public Local_Images_Adapter(ArrayList<Uri> images, Context c) {
        this.images = images;
        this.c = c;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.local_img_format,viewGroup,false);

        return new LocalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder localViewHolder, final int i) {

        Uri uri=images.get(i);
        Picasso.get().load(uri).into(localViewHolder.im);


        localViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c,View_Local_Image.class);
                intent.putExtra("img",images.get(i).toString());

                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

}
