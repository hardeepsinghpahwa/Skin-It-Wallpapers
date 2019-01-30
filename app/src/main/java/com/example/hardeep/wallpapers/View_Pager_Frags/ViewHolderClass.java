package com.example.hardeep.wallpapers.View_Pager_Frags;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hardeep.wallpapers.GlideApp;
import com.example.hardeep.wallpapers.R;

public class ViewHolderClass extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;

    public ViewHolderClass(@NonNull View itemView) {
        super(itemView);

        textView=itemView.findViewById(R.id.cardtext);
        imageView=itemView.findViewById(R.id.cardimage);
    }

    public void setText(String title)
    {
        textView.setText(title);
    }


    public void setImage(Context c,String img)
    {
        GlideApp.with(c).load(img).centerCrop().thumbnail(0.5f).into(imageView);
    }
}
