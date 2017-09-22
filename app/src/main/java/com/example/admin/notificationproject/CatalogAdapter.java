package com.example.admin.notificationproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Admin on 9/7/2017.
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.MyViewHolder> {


        private Activity context;
        private List<Catalog> catalogList;
        private Activity applicationContext;

        public CatalogAdapter(Activity context, List<Catalog> catalogList) {
                this.context = context;
                this.catalogList = catalogList;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_calog,null);

                MyViewHolder myViewHolder = new MyViewHolder(view);
                return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
                final Catalog catalog = catalogList.get(position);
                holder.item1.setText(catalog.getAssetTitle());
                Glide.with(context)
                        .load(catalog.getCatalogimageurl())
                        .into(holder.imageButton1);


                holder.imageButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Catalog c = catalog;
                                Intent intent = new Intent(context,DescriptionActivity
                                        .class);
                                intent.putExtra("select", c);
                                context.startActivity(intent);




                        }
                });

        }

        @Override
        public int getItemCount() {
                return (null != catalogList ? catalogList.size() : 0);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
                TextView item1;
                ImageButton imageButton1;


                public MyViewHolder(View itemView) {
                        super(itemView);
                        item1 = itemView.findViewById(R.id.item1);
                        imageButton1 = itemView.findViewById(R.id.imageButton1);
                }
        }
}