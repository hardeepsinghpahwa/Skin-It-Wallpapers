package com.pahwa.hardeep.wallpapers.View_Pager_Frags;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.pahwa.hardeep.wallpapers.R;

class LocalViewHolder extends RecyclerView.ViewHolder{

    ImageView im;
    public LocalViewHolder(@NonNull View itemView) {
        super(itemView);
        im=itemView.findViewById(R.id.pic);
    }
}
