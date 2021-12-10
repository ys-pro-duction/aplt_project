package com.ys_production.aveeplayerlatesttemplate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.downloader.PRDownloader;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ys_production.aveeplayerlatesttemplate.databinding.ActivityNavigationMainActivtyBinding;
import com.ys_production.aveeplayerlatesttemplate.ui.gallery.GalleryFragment;
import com.ys_production.aveeplayerlatesttemplate.ui.home.HomeFragment;

public class Navigation_MainActivty extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationMainActivtyBinding binding;

    private InterstitialAd mInterstitialAd;
    public static int a = 0;
    public int f = 0;
    public static int backint = 1;
    public FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationMainActivtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFabclick();
//                if(f==0){
//                    goTodownloads(fab);
//                    loadAds();
//                    showAd();
//                }
//                else {
//                    goTohome(fab);
//                    loadAds();
//                    showAd();
//                }
//                Toast.makeText(Navigation_MainActivty.this, "try ad", Toast.LENGTH_SHORT).show();
//            }
//        });

//        =========================AD load============================
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(Navigation_MainActivty.this, "Initialize DoNe", Toast.LENGTH_SHORT).show();
            }
        });


//        if(a>0) {
//
//            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    // Called when fullscreen content is dismissed.
//                    showAd();
//                    Toast.makeText(Navigation_MainActivty.this,"ad closed",Toast.LENGTH_SHORT);
//                    Log.d("TAG", "The ad was dismissed.");
//                }
//
//            @Override
//            public void onAdFailedToShowFullScreenContent(AdError adError) {
//                // Called when fullscreen content failed to show.
//                Log.d("TAG", "The ad failed to show.");
//            }
//
//            @Override
//            public void onAdShowedFullScreenContent() {
//                // Called when fullscreen content is shown.
//                // Make sure to set your reference to null so you don't
//                // show it a second time.
//                mInterstitialAd = null;
//                Log.d("TAG", "The ad was shown.");
//            }
//            });
//        }


//===================== Runtime Permission Check ===================================================
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (Build.VERSION.SDK_INT >= 30){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",new Object[]{
                        getApplicationContext().getPackageName()
                })));
                startActivityForResult(intent,2000);
            }catch (Exception e){
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent,2000);
            }
        }
        PRDownloader.initialize(this);

        setSupportActionBar(binding.appBarNavigationMainActivty.toolbar);
//        binding.appBarNavigationMainActivty.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_main_activty);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__main_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.rateapp){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp.w4b")));
            Toast.makeText(this, "Already rated 1 year ago", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.aboutus){
            Toast.makeText(this, "🖕🖕🖕🖕", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_main_activty);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    public void loadAds(){
//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712"
//                , adRequest, new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        super.onAdLoaded(interstitialAd);
//                        mInterstitialAd = interstitialAd;
//                        Log.i("Ad1", "AdLoaded");
//                    }
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        super.onAdFailedToLoad(loadAdError);
//                        Log.i("error load Ad", loadAdError.getMessage());
//                        mInterstitialAd = null;
//                    }
//                });
//    }
//
//    public void showAd(){
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mInterstitialAd != null) {
//                    mInterstitialAd.show(Navigation_MainActivty.this);
//                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            // Called when fullscreen content is dismissed.
//                            Toast.makeText(Navigation_MainActivty.this, "ad closed", Toast.LENGTH_LONG);
//                            loadAds();
//                        }
//                    });
//                } else {
//                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
//                }
//            }
//        },1000);
//    }
//
//    public void goTohome(){
//        HomeFragment homeFragment = new HomeFragment();
//        GalleryFragment galleryFragment = new GalleryFragment();
//
////        FragmentManager fm = getFragmentManager();
////        android.app.FragmentManager fm = getFragmentManager();
////        fm.beginTransaction()
////                .replace(R.id.fragmentHomeId,homeFragment)
////                .commit();
////        fab.setImageResource(android.R.drawable.stat_sys_download_done);
////        f--;
//        FragmentManager fragmentManager = getSupportFragmentManager();
////        fragmentManager.beginTransaction().replace(R.id.contaner,homeFragment).commit();
//        fragmentManager.beginTransaction().replace(R.id.fragmentHomeId,homeFragment).commit();
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setImageResource(R.drawable.ic_baseline_home_24);
//        f++;
//    }
//    public void goTodownloads(){
//        GalleryFragment galleryFragment = new GalleryFragment();
//        HomeFragment homeFragment = new HomeFragment();
//
//
////        FragmentManager fm = getFragmentManager();
////        fm.beginTransaction()
////                .replace(R.id.fragmentHomeId,galleryFragment)
////                .commit();
//        FragmentManager fragmentManager = getSupportFragmentManager();
////        fragmentManager.beginTransaction().replace(R.id.contaner,galleryFragment).commit();
//        fragmentManager.beginTransaction().replace(R.id.fragmentHomeId,galleryFragment).commit();
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setImageResource(android.R.drawable.stat_sys_download_done);
//        f--;
//    }
//    public void onFabclick(){
//        if(f==0){
//            goTodownloads();
//            loadAds();
//            showAd();
//        }
//        else {
//            goTohome();

    @Override
    public void onBackPressed() {
        if(backint == 2){
            super.onBackPressed();
        }else {
            Toast.makeText(Navigation_MainActivty.this, "press again to exit", Toast.LENGTH_SHORT).show();
            backint++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backint--;
                }
            },1500);
        }
    }


//            loadAds();
//            showAd();
//        }
//        Toast.makeText(Navigation_MainActivty.this, "try ad", Toast.LENGTH_SHORT).show();
//
//    }
}