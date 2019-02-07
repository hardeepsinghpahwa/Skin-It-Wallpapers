package com.example.hardeep.wallpapers;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class Image_Download extends AppCompatActivity {

    FloatingActionButton downloadbutton;
    ImageView done;
    DatabaseReference databaseReference;
    ProgressDialog p;
    ProgressBar progressBar;
    String url,position;
    AsyncTask mMyTask;
    ImageView previewimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__download);

        getSupportActionBar().setTitle("Preview Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        downloadbutton = findViewById(R.id.downloadbutton);
        previewimg = findViewById(R.id.previewimage);
        progressBar=findViewById(R.id.pbarr);
        done=findViewById(R.id.downloaddone);
        position=getIntent().getStringExtra("pos");
        databaseReference=FirebaseDatabase.getInstance().getReference();

        p=new ProgressDialog(Image_Download.this);
        p.setTitle("Please wait");
        p.setCanceledOnTouchOutside(false);
        p.setMessage("Downloading");
        url=getIntent().getStringExtra("url");
        Log.i("URL",url);

        GlideApp.with(getApplicationContext()).load(url).fitCenter().listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(Image_Download.this,"Load Failed! Check Your connection",Toast.LENGTH_SHORT);
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(previewimg);

        downloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkWriteExternalPermission())
                {
                    p.show();
                    mMyTask = new DownloadTask()
                            .execute(stringToURL(url));
                }else {
                    ActivityCompat.requestPermissions(Image_Download.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

    }

    private class DownloadTask extends AsyncTask<URL,Void,Bitmap>{

        protected Bitmap doInBackground(URL...urls){
            URL url = urls[0];
            HttpURLConnection connection = null;

            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();


                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                return bmp;

            }catch(IOException e){
                e.printStackTrace();
            }finally{
                connection.disconnect();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result){

            p.dismiss();
            downloadbutton.setVisibility(View.GONE);
            done.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Image Downloaded", Toast.LENGTH_LONG).show();
            if(result!=null){
                saveImageToInternalStorage(result);
            }else {
                Toast.makeText(getApplicationContext(),"Error Downloading Image",Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected URL stringToURL(String urlString){
        try{
            URL url = new URL(urlString);
            return url;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

    protected Uri saveImageToInternalStorage(Bitmap bitmap) {

        String savedImagePath = null;

        String imageFileName = UUID.randomUUID().toString() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/Downloaded Images");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            galleryAddPic(savedImagePath);
        }
        Uri savedImageURI = Uri.parse(savedImagePath);

        return savedImageURI;
    }

    private boolean checkWriteExternalPermission()
    {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
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
