package com.ys_production.aveeplayerlatesttemplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdepter extends RecyclerView.Adapter<MyAdepter.MyViewHolder> {
    Context context;

    ArrayList<Template_data> list;

    public MyAdepter(Context context, ArrayList<Template_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Template_data item = list.get(position);
        holder.Item_name.setText(item.getItem_name());

        holder.Durl.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Download starting \n"+item.getItem_name(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,MainActivity2.class);
                intent.putExtra("ImageUrl",item.getImgurl());
                intent.putExtra("Name",item.getItem_name());
                intent.putExtra("DownloadUrl",item.getDlink());
                context.startActivity(intent);
            }
        });


//        ImageView imageView =holder.ImageUrl.findViewById(R.id.imageView1);
        Glide
                .with(context)
                .load(item.getImgurl())
                .into(holder.ImageUrl);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ImageUrl;
        Button Durl;
        TextView Item_name;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ImageUrl = itemView.findViewById(R.id.imageView1);
            Durl = itemView.findViewById(R.id.button1);
            Item_name = itemView.findViewById(R.id.textView1);

        }
    }
}