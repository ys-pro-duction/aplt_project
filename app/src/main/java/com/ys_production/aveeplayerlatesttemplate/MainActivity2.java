package com.ys_production.aveeplayerlatesttemplate;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.File;

public class MainActivity2 extends AppCompatActivity {

//    private int diractory_check = 0;
    private ConstraintLayout rootLayout;
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    private String API_KEY = "AIzaSyCYsgWwGbsY9mzcDsubkWdYi7w0aKPWt9Q";
    public String VIDEO_ID = null;


    @Override
    public void onBackPressed() {
        PRDownloader.cancelAll();


        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rootLayout = findViewById(R.id.ma2);
        TextView tv = findViewById(R.id.textView4);
        PRDownloader.initialize(this);
        VIDEO_ID = getIntent().getStringExtra("Yt");
//        ==============Set Template Name =======================
        TextView textView2 = findViewById(R.id.textView2);
        String name = getIntent().getStringExtra("Name");
        textView2.setText(name);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading.....");

//===========================Rewarded ad========================================
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
//                Toast.makeText(MainActivity2.this, "Initialize DoNe", Toast.LENGTH_SHORT).show();

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            SharedPreferences sp = getSharedPreferences("adswitch", Context.MODE_PRIVATE);
            String ads = String.valueOf(sp.getString("ADswitch","on"));
            Button adownload = findViewById(R.id.again_download);
            banner2(ads);
            adownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    if (ads.equals("on")){
                        loadRad(tv,name,progressDialog);
                    }
                    else {
                        startDownload(tv,name,progressDialog);
                    }
                }
            });


        }
        else {
            Intent i = new Intent(this, Navigation_MainActivty.class);
            startActivity(i);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }




//        =====================Youtube load==============================
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.ytView);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                super.onReady(youTubePlayer);
                if (VIDEO_ID !=  null){
                    youTubePlayer.loadVideo(VIDEO_ID,0);
                }else {
                    Toast.makeText(MainActivity2.this,"preview not availeble",Toast.LENGTH_SHORT);
                }
            }
        });

//========================Template_prewview_image===================================================
        ImageView imageView2 = findViewById(R.id.imageView2);
            Glide.with(this).load(getIntent().getStringExtra("ImageUrl")).placeholder(R.drawable.ic_baseline_home_24).into(imageView2);


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



        }
        public void showRad(TextView tv, String name,ProgressDialog progressDialog){
            if (mRewardedAd != null) {
                Activity activityContext = MainActivity2.this;
                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.d(TAG, "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                        Actionad(tv,name,progressDialog);
                    }
                });
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
            }
        }

        public void Actionad(TextView tv, String name,ProgressDialog progressDialog){
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    startDownload(tv,name,progressDialog);
                    Log.d(TAG, "Ad was shown.");
                    Toast.makeText(MainActivity2.this, "ad showing", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    startDownload(tv,name,progressDialog);
                    Log.d(TAG, "Ad failed to show.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad was dismissed.");
//                    mRewardedAd = null;
                    startDownload(tv,name,progressDialog);
                }

            });
        }

        public void loadRad(TextView tv, String name,ProgressDialog progressDialog){
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            startDownload(tv, name,progressDialog);
                            Log.d(TAG, loadAdError.getMessage());
                            mRewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;
                            showRad(tv,name,progressDialog);
                            Log.d(TAG, "Ad was loaded.");
                        }
                    });
        }

        public void startDownload(TextView tv, String name,ProgressDialog progressDialog){
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
                            progressDialog.dismiss();
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

        }

    //========================Banner_Ad===============================
    public void banner2(String ads) {
        if (ads.equals("on")) {
            AdView mAdView = findViewById(R.id.banner2);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });
        }
    }
}