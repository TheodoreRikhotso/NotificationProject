package com.example.admin.notificationproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/7/2017.
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.MyViewHolder>   implements Filterable{


        private Activity context;
        private List<Catalog> catalogList,catalogss;
        private Activity applicationContext;
        private   Catalog catalog;

        public CatalogAdapter(Activity context, List<Catalog> catalogList) {
                this.context = context;
                this.catalogList = catalogList;

                this.catalogss=catalogList;
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


                holder.btnView.setOnClickListener(new View.OnClickListener() {
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
                TextView item1, textView15, textView9,textView22, textView1;
                ImageView imageButton1 , imb2,imb1 ;
                View view01,view02 ;
                Button btnView;
                boolean isPressed;

                public MyViewHolder(View itemView) {
                        super(itemView);
                        item1 = itemView.findViewById(R.id.item1);
                        textView15 = itemView.findViewById(R.id.textView15);
                        textView9 = itemView.findViewById(R.id.textView9);
                        textView22 = itemView.findViewById(R.id.textView22);
                        textView1 = itemView.findViewById(R.id.textView1);
                        imageButton1 = itemView.findViewById(R.id.imageButton1);
                        imb2 = itemView.findViewById(R.id.imb2);
                        imb1 = itemView.findViewById(R.id.imb1);
                        view01 = itemView.findViewById(R.id.view01);
                        view02 = itemView.findViewById(R.id.view02);
                        btnView = itemView.findViewById(R.id.btnView);

//                        //FUEL ICON COLOR CHANGE
                        textView22.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        if (isPressed) {
                                                textView22.setTextColor(Color.parseColor("#808080"));
                                                view02.setBackgroundColor(Color.parseColor("#808080"));
                                        } else {
                                                textView22.setTextColor(Color.parseColor("#ec669900"));
                                                view02.setBackgroundColor(Color.parseColor("#ec669900"));
                                        }
                                        isPressed = !isPressed; // reverse
                                }
                        });
                        textView15.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        if (isPressed) {
                                                textView15.setTextColor(Color.parseColor("#808080"));
                                                view01.setBackgroundColor(Color.parseColor("#808080"));
                                        } else {
                                                textView15.setTextColor(Color.parseColor("#ec669900"));
                                                view01.setBackgroundColor(Color.parseColor("#ec669900"));
                                        }
                                        isPressed = !isPressed; // reverse
                                }
                        });
//
//
//                        //SPEED ICON COLOR CHANGE
                        imb1.setBackgroundResource(R.drawable.carseat);
                        imb1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        if (isPressed) {
                                                textView1.setTextColor(Color.parseColor("#808080"));//gray
                                                view.setBackgroundResource(R.drawable.carseat);
                                        } else {
                                                textView1.setTextColor(Color.parseColor("#ec669900"));//orange
                                                view.setBackgroundResource(R.drawable.carseat01);
                                        }
                                        isPressed = !isPressed; // reverse
                                }
                        });
                        imb2.setBackgroundResource(R.drawable.cardoor01);
                        imb2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        if (isPressed) {
                                                textView9.setTextColor(Color.parseColor("#808080"));//gray
                                                view.setBackgroundResource(R.drawable.cardoor01);
                                        } else {
                                                textView9.setTextColor(Color.parseColor("#ec669900"));//orange
                                                view.setBackgroundResource(R.drawable.cardoor02);
                                        }
                                        isPressed = !isPressed; // reverse
                                }
                        });
                }
        }





        @Override
        public Filter getFilter() {


                return new Filter() {
                        @Override
                        protected FilterResults performFiltering(CharSequence charSequence) {

                                String charString = charSequence.toString();

                                if (charString.isEmpty()) {

                                        catalogList = catalogss;
                                } else {

                                        ArrayList<Catalog> filteredList = new ArrayList<>();

                                        for (Catalog androidVersion : catalogss) {

                                                if (androidVersion.getAssetTitle().toLowerCase().contains(charString) ) {

                                                        filteredList.add(androidVersion);
                                                }
                                        }

                                        catalogList = filteredList;
                                }

                                FilterResults filterResults = new FilterResults();
                                filterResults.values = catalogList;
                                return filterResults;
                        }

                        @Override
                        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                                catalogList = (ArrayList<Catalog>) filterResults.values;
                                notifyDataSetChanged();
                        }
                };
        }
}