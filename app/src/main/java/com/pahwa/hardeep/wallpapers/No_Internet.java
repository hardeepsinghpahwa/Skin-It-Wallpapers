package com.pahwa.hardeep.wallpapers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class No_Internet extends AppCompatActivity {

    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no__internet);
        retry=findViewById(R.id.retrybutton);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInternetAvailable())
                {
                    finish();
                    startActivity(new Intent(No_Internet.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(No_Internet.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }
}
