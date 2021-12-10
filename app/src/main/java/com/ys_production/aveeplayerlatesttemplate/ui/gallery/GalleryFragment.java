package com.ys_production.aveeplayerlatesttemplate.ui.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import java.util.ArrayList;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ys_production.aveeplayerlatesttemplate.R;
import com.ys_production.aveeplayerlatesttemplate.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
    ListView listView0;
    String[] items0;
    private TextView textView_file_0;

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView0 = root.findViewById(R.id.listView);
        textView_file_0 = root.findViewById(R.id.kagadjkf);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displaysongs();
                }
            }, 1);
        }
        else{
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
//==================== Get .Viz and put in Arraylist======================
    public ArrayList<File> findSong(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        assert files != null;
        for (File singlefile : files) {
            if (singlefile.isDirectory() && !singlefile.isHidden()) {
                arrayList.addAll(findSong(singlefile));
            } else {
                if (singlefile.getName().endsWith(".viz")) {
                    arrayList.add(singlefile);
                }
            }
        }
        return arrayList;
    }
//===============Display Template & Open template==============================
    void displaysongs() {
        final ArrayList<File> mySongs = findSong(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download"));
        items0 = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            items0[i] = mySongs.get(i).getName();
        }
        ArrayAdapter<String> myAdepter = new ArrayAdapter<>(getContext(), android.R.layout.simple_expandable_list_item_1, items0);
        listView0.setAdapter(myAdepter);
        listView0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = items0[position];
                if (Build.VERSION.SDK_INT >= 23){
                    open_File(fileName);
                }else {
                    Open_file2(fileName);
                }
            }
        });
    }
//=================Open template 23 and more============================
    public void open_File(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download", fileName);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//        ====================================================
        final Uri data = FileProvider.getUriForFile(getContext(), "com.ys_production.aveeplayerlatesttemplate"+".provider",
                new File(String.valueOf(file)));

        intent.setDataAndType(data, "application/octet-stream");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent1 = Intent.createChooser(intent, "Avee player");
        try {
            getContext().startActivity(intent1);
            textView_file_0.setText(String.valueOf(data));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Please install Avee Player", Toast.LENGTH_SHORT).show();
        }
    }
//=================Open template 22 and down============================
    public void Open_file2(String fileName){
        Intent intent = new Intent();
        String name = fileName.replace(" ","%20");
        intent.setAction(Intent.ACTION_VIEW);
        Uri data = Uri.parse(String.valueOf("file://"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/"+name));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(data,"application/octet-stream");
        textView_file_0.setText(String.valueOf(data));
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getContext(), "Please install Avee Player", Toast.LENGTH_SHORT).show();
        }

    }



}