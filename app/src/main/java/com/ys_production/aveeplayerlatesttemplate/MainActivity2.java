package com.ys_production.aveeplayerlatesttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity2 extends AppCompatActivity {

    private int diractory_check = 0;
    private ConstraintLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rootLayout = findViewById(R.id.ma2);
        TextView tv = findViewById(R.id.textView4);
        PRDownloader.initialize(this);

//===========================LOADING_bar=============================================================

//        ImageView imageView4 = findViewById(R.id.imageView4);
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Glide.with(MainActivity2.this).load("https://firebasestorage.googleapis.com/v0/b/avee-player-latest-templ-59481.appspot.com/o/PicsArt_10-26-01.43.40.jpg?alt=media&token=7ec9b0d8-2b31-482f-95d0-c22eb3a45e67")
//                        .into(imageView4);
//                Toast.makeText(MainActivity2.this, "Download Done", Toast.LENGTH_LONG).show();
//            }
//        },6000);
//
//        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/avee-player-latest-templ-59481.appspot.com/o/Barline-Loading-Images-1.gif?alt=media&token=c60b839d-033e-45af-bce5-22dc354229c3")
//                .into(imageView4);

//========================Template_prewview_image===================================================
        ImageView imageView2 = findViewById(R.id.imageView2);
        Glide.with(this).load(getIntent().getStringExtra("ImageUrl")).into(imageView2);

//        ==============Set Template Name =======================
        TextView textView2 = findViewById(R.id.textView2);
        String name = getIntent().getStringExtra("Name");
        textView2.setText(name);

//        ==============Start Download====================
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
////
////
////
////            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getIntent().getStringExtra("DownloadUrl")));
//            String title = URLUtil.guessFileName(getIntent().getStringExtra("DownloadUrl"), null, null);
//            request.setTitle(title);
//            request.setDescription("Downloading..");
//            String cookie = CookieManager.getInstance().getCookie(getIntent().getStringExtra("DownloadUrl"));
//            request.addRequestHeader("coockie", cookie);
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name+".viz");
//            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//            downloadManager.enqueue(request);
//            Toast.makeText(this,"Download Startd..",Toast.LENGTH_SHORT);
//        }
//        else{
//            Intent OpenMainActivry = new Intent(MainActivity2.this,MainActivity2.class);
//            startActivity(OpenMainActivry);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//
//            Toast.makeText(this,"error in download",Toast.LENGTH_LONG);
//        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


            String url = String.valueOf(Uri.parse(getIntent().getStringExtra("DownloadUrl")));
            File dirPath = new File(String.valueOf(Environment.getExternalStoragePublicDirectory("Download")));
            //        String dirPath1 = String.valueOf(dirPath);
            String fileName = name + ".viz";
            ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Downloading...");
            pd.setCancelable(true);
            tv.setText(String.valueOf(dirPath.getPath()));


            PRDownloader.download(url, dirPath.getPath(), fileName)
                    .build()
                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                        @Override
                        public void onStartOrResume() {
                            Toast.makeText(MainActivity2.this, "start Dwnld", Toast.LENGTH_LONG).show();

                        }
                    })
                    .setOnPauseListener(new OnPauseListener() {
                        @Override
                        public void onPause() {

                        }
                    })
                    .setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel() {
                            Snackbar.make(rootLayout, "Download Cancel", Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(Progress progress) {
                            long par = progress.currentBytes * 100 / progress.totalBytes;
                            pd.setMessage("Downloading.. : " + par + "%");
                            pd.show();

//                            onBackPressed(pd.cancel(););

                        }
                    })
                    .start(new OnDownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            pd.dismiss();

                            Snackbar.make(rootLayout, "Download Done", Snackbar.LENGTH_LONG).show();

                        }

                        @Override
                        public void onError(Error error) {

                            Snackbar.make(rootLayout, "Error when Downloading", Snackbar.LENGTH_LONG).show();
                        }
                    });
        }else {
            Intent i = new Intent(this,Navigation_MainActivty.class);
            startActivity(i);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }


        }
//        public void onBackPressed(ProgressDialog pd){
//        getOnBackPressedDispatcher().addCallback();
//        }


    }