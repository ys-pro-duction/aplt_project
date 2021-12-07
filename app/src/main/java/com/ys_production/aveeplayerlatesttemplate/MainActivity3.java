package com.ys_production.aveeplayerlatesttemplate;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    ListView listView;
    String[] items;
    private TextView textView_file_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView = findViewById(R.id.listView);
        textView_file_ = findViewById(R.id.kagadjkf);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displaysongs();
            }
        }, 100);
    }
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

    void displaysongs() {
        final ArrayList<File> mySongs = findSong(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
        items = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName();
        }
        ArrayAdapter<String> myAdepter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, items);
        listView.setAdapter(myAdepter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = items[position];
                if (Build.VERSION.SDK_INT >= 23){
                    open_File(fileName);
                }else {
                    Toast.makeText(MainActivity3.this, "Buy new Phone", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//=======================================================================================================================
    public void open_File(String filename) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download", filename);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//        ====================================================
        final Uri data = FileProvider.getUriForFile(this, getPackageName()+".provider", new File(String.valueOf(file)));
        this.grantUriPermission(this.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(data, "application/octet-stream");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent1 = Intent.createChooser(intent, "Avee player");
        try {
            MainActivity3.this.startActivity(intent1);
            textView_file_.setText(String.valueOf(data));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Please install Avee Player", Toast.LENGTH_SHORT).show();
        }
    }
}