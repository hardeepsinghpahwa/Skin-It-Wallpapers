package com.example.hardeep.wallpapers.View_Pager_Frags;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.hardeep.wallpapers.R;
import com.squareup.picasso.Picasso;

class LocalViewHolder extends RecyclerView.ViewHolder{

    ImageView im;
    public LocalViewHolder(@NonNull View itemView) {
        super(itemView);
        im=itemView.findViewById(R.id.pic);
    }
}
