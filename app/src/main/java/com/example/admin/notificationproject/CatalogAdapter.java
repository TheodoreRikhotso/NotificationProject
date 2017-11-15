package com.example.admin.notificationproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/7/2017.
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.MyViewHolder> implements Filterable{


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
                holder.item1.setText(catalog.getTitle());
                Glide.with(context)
                        .load(catalog.getImage())
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
                TextView item1, textView15, textView9,textView22, textView1;
                ImageView imageButton1 , imb2,imb1 ;
                View view01,view02 ;
                Button btnView;
                boolean isPressed;
                RelativeLayout relativeLayout;

                public MyViewHolder(View itemView) {
                        super(itemView);
                        item1 = itemView.findViewById(R.id.item1);
                        imageButton1 = itemView.findViewById(R.id.imageColog);
                        relativeLayout = itemView.findViewById(R.id.relativeLayout);


//                        //FUEL ICON COLOR CHANGE
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