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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 09-Oct-17.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder>   implements Filterable {


    private Activity context;
    private List<PhonePojo> catalogList, catalogss;
    private Activity applicationContext;
    private PhonePojo catalog;

    public PhoneAdapter(Activity context, List<PhonePojo> catalogList) {
        this.context = context;
        this.catalogList = catalogList;

        this.catalogss = catalogList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_models_activity, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PhonePojo catalog = catalogList.get(position);
        holder.item1.setText(catalog.getTitle());
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
        holder.imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty>0) {
                PhonePojo c = catalog;
                Intent intent = new Intent(context, PhoneDescriptionActivity
                        .class);
                intent.putExtra("select", c);
                context.startActivity(intent);
                }else {
                    Toast.makeText(context, catalog.getTitle()+" is not available please try tomorrow", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != catalogList ? catalogList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item1, txtStatus, textView9,textView22, textView1;
        ImageView imageButton1 , imb2,imb1 ;
        View view01,view02 ;
        Button btnView;
        boolean isPressed;


        public MyViewHolder(View itemView) {
            super(itemView);
            item1 = itemView.findViewById(R.id.item2);
            imageButton1 = itemView.findViewById(R.id.imageButton2);
            txtStatus = itemView.findViewById(R.id.textView26);

//

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

                    ArrayList<PhonePojo> filteredList = new ArrayList<>();

                    for (PhonePojo androidVersion : catalogss) {

                        if (androidVersion.getTitle().toLowerCase().contains(charString)) {

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
                catalogList = (ArrayList<PhonePojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
