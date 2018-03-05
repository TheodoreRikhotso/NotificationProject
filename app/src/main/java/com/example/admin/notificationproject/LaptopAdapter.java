package com.example.admin.notificationproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 09-Oct-17.
 */

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.MyViewHolder>   implements Filterable {


    private Activity context;
    private List<Laptop> catalogList, catalogss;
    private Activity applicationContext;
    private Laptop catalog;

    public LaptopAdapter(Activity context, List<Laptop> catalogList) {
        this.context = context;
        this.catalogList = catalogList;

        this.catalogss = catalogList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_laptop, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Laptop catalog = catalogList.get(position);
        holder.item3.setText(catalog.getTitle());
        Glide.with(context)
                .load(catalog.getImage())
                .into(holder.imageButton1);

        final int qty = Integer.parseInt(catalog.getTotalQuantity());

        if(qty >0) {
            holder.txtStatus.setText("Available");


        }
        else{
            holder.txtStatus.setText("Unavailable");
            holder.txtStatus.setTextColor(Color.parseColor("#fa1414"));

        }


        holder.linearL0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty>0) {
                    Laptop c = catalog;
                    Intent intent = new Intent(context, LaptopDescriptionActivity
                            .class);
                    intent.putExtra("select", c);
                    context.startActivity(intent);
                }else {
                    Snackbar.make(view, catalog.getTitle()+" is not available please try tomorrow",Snackbar.LENGTH_LONG).show();
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != catalogList ? catalogList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item3;

        TextView item1, txtStatus, textView9,textView22, textView1;
        ImageView imageButton1 , imb2,imb1 ;
        View view01,view02 ;
        Button btnView;
        boolean isPressed;
        LinearLayout linearL0;


        public MyViewHolder(View itemView) {
            super(itemView);
            item3 = itemView.findViewById(R.id.item3);
            imageButton1 = itemView.findViewById(R.id.imageButton3);
            txtStatus = itemView.findViewById(R.id.txtStatus1);
            item1 = itemView.findViewById(R.id.item1);
            linearL0 = itemView.findViewById(R.id.linearL0);


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

                    ArrayList<Laptop> filteredList = new ArrayList<>();

                    for (Laptop androidVersion : catalogss) {

                        if (androidVersion.getTitle().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                            //System.out.println("Something found" + filteredList.size());


                        }
//
                    }

                    catalogList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = catalogList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                catalogList = (ArrayList<Laptop>) filterResults.values;


                if (catalogList.size() == 0){
                    System.out.println("Nothing found....");
                            Toast.makeText(context, "Search result not Found", Toast.LENGTH_SHORT).show();
                        } else {
                            //System.out.println("Something found....");

                        }
                notifyDataSetChanged();
            }
        };
    }
}
