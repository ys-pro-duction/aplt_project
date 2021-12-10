package com.ys_production.aveeplayerlatesttemplate.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ys_production.aveeplayerlatesttemplate.MyAdepter;
import com.ys_production.aveeplayerlatesttemplate.Navigation_MainActivty;
import com.ys_production.aveeplayerlatesttemplate.R;
import com.ys_production.aveeplayerlatesttemplate.Template_data;
import com.ys_production.aveeplayerlatesttemplate.databinding.FragmentHomeBinding;
import com.ys_production.aveeplayerlatesttemplate.ui.gallery.GalleryFragment;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdepter myAdepter;
    ArrayList<Template_data> list;
    static int fabp = 0;
    private InterstitialAd mInterstitialAd;




    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        ==================AdMob ad======================================================

        MobileAds.initialize(root.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(root.getContext(), "Initialize DoNe", Toast.LENGTH_SHORT).show();
            }
        });
//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(root.getContext(), "ca-app-pub-3940256099942544/1033173712"
//                , adRequest, new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        super.onAdLoaded(interstitialAd);
//                        mInterstitialAd = interstitialAd;
//                        Log.i("Ad1", "AdLoaded");
//
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        super.onAdFailedToLoad(loadAdError);
//                        Log.i("error load Ad", loadAdError.getMessage());
//                        mInterstitialAd = null;
//                    }
//                });
//        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
//            @Override
//            public void onAdDismissedFullScreenContent() {
//                // Called when fullscreen content is dismissed.
//                Log.d("TAG", "The ad was dismissed.");
//            }
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
//        });




        //================= Recycler View Setup ============================================================

        recyclerView = root.findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        myAdepter = new MyAdepter(getContext(),list);
        recyclerView.setAdapter(myAdepter);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()){

                    Template_data template_data = datasnapshot.getValue(Template_data.class);
                    list.add(template_data);
                }
                myAdepter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();

            }
        });

//        //===================== Runtime Permission Check ===================================================
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
////            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }

//================ Open Fab ========================================================================
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabclick(fab);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void goTohome(FloatingActionButton fab){
        HomeFragment homeFragment = new HomeFragment();

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragmentHomeId, homeFragment)
                .commit();
        fab.setImageResource(android.R.drawable.stat_sys_download_done);
        fabp--;
    }
    public void goTodownloads(FloatingActionButton fab){
        GalleryFragment galleryFragment = new GalleryFragment();

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragmentHomeId,galleryFragment)
                .commit();
        fab.setImageResource(R.drawable.ic_baseline_home_24);
        fabp++;
    }
//    ==================ad=============================
public void loadAds(){
    AdRequest adRequest = new AdRequest.Builder().build();
    InterstitialAd.load(getContext(), "ca-app-pub-3940256099942544/1033173712"
            , adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    mInterstitialAd = interstitialAd;
                    Log.i("Ad1", "AdLoaded");
                }
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.i("error load Ad", loadAdError.getMessage());
                    mInterstitialAd = null;
                }
            });


}

    public void showAd(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(getActivity());
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            Toast.makeText(getContext(), "ad closed", Toast.LENGTH_LONG);
                            loadAds();
                        }
                    });
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        },1000);
    }
    public void onFabclick(FloatingActionButton fab){
        SharedPreferences sp = getContext().getSharedPreferences("adswitch", Context.MODE_PRIVATE);
        String ads = String.valueOf(sp.getString("ADswitch","on"));
        if(fabp==0){
            goTodownloads(fab);
            Toast.makeText(getContext(), "ads "+ads, Toast.LENGTH_SHORT).show();
            if (ads.equals("on")){
                loadAds();
                showAd();
                Toast.makeText(getContext(), "try ad", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            goTohome(fab);
            if (ads.equals("on")){
                loadAds();
                showAd();
                Toast.makeText(getContext(), "try ad", Toast.LENGTH_SHORT).show();
            }
        }


    }
}