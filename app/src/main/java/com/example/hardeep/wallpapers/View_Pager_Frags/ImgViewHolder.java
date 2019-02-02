package com.example.hardeep.wallpapers.View_Pager_Frags;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hardeep.wallpapers.GlideApp;
import com.example.hardeep.wallpapers.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class ImgViewHolder extends RecyclerView.ViewHolder {

    private View v;
    public ImageView img;
    private ProgressBar p;

    public ImgViewHolder(@NonNull View itemView) {
        super(itemView);

        v=itemView;
        int color1[]={255,255,255,229,204,204,204,204,204,229,204,255,204,224,172,255,255,255,224,152,255};
        int color2[]={204,229,255,255,255,255,255,229,204,204,255,255,153,224,238,228,228,228,255,251,160};
        int color3[]={204,204,204,204,204,229,255,255,255,255,153,153,255,224,238,225,181,196,255,152,122};
        ArrayList<Integer> colors=new ArrayList<>();

        for(int i=0;i<color1.length;i++) {
            int color = Color.rgb(color1[i], color2[i], color3[i]);
            colors.add(color);
        }

        Random random=new Random();
        int index=random.nextInt(colors.size());
        itemView.setBackgroundColor(colors.get(index));
    }

    public void setImage(Context c, String image) {
        img = v.findViewById(R.id.pic);
        p = v.findViewById(R.id.pbar);
        p.setVisibility(View.VISIBLE);
        Picasso.get().load(image).resize(350,283).centerCrop().into(img, new Callback() {
            @Override
            public void onSuccess() {
                p.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
