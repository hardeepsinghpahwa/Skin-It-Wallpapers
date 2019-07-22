package com.pahwa.hardeep.wallpapers.NavigationFrags;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pahwa.hardeep.wallpapers.R;

public class Privacy_Policy extends Fragment {

    Button privacypolicy;

    public Privacy_Policy() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_privacy__policy, container, false);

        privacypolicy=v.findViewById(R.id.privacypol);

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hardeepsingh3485in.wixsite.com/website/store-policies"));
                startActivity(browserIntent);
            }
        });

        return v;

    }
}