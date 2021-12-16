package com.ys_production.aveeplayerlatesttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class splash_screen extends AppCompatActivity {
    public FirebaseDatabase database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getSupportActionBar().hide();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Ads");
        mRef.child("AdUnit").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,Object> adunit = (Map<String, Object>) snapshot.getValue();

                String ma2r = adunit.get("ma2r").toString();
                String ma2_b = adunit.get("ma2_b").toString();
                String gaf_b = adunit.get("gaf_b").toString();
                String gaf_i = adunit.get("gaf_i").toString();
                String hof_i = adunit.get("hof_i").toString();

                SharedPreferences sp = getSharedPreferences("adunit",MODE_PRIVATE);
                SharedPreferences.Editor sped = sp.edit();

                sped.putString("ma2r",ma2r).apply();
                sped.putString("ma2_b",ma2_b).apply();
                sped.putString("gaf_b",gaf_b).apply();
                sped.putString("gaf_i",gaf_i).apply();
                sped.putString("hof_i",hof_i).apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("adunit error","error");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splash_screen.this,Navigation_MainActivty.class);
                startActivity(i);
                finish();
            }
        },500);

    }
}