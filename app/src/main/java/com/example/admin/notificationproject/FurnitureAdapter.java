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
 * Created by Admin on 18-Oct-17.
 */

public class FurnitureAdapter extends RecyclerView.Adapter<FurnitureAdapter.MyViewHolder>   implements Filterable {


    private Activity context;
    private List<FurniturePojo> catalogList, catalogss;
    private Activity applicationContext;
    private FurniturePojo catalog;

    public FurnitureAdapter(Activity context, List<FurniturePojo> catalogList) {
        this.context = context;
        this.catalogList = catalogList;

        this.catalogss = catalogList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_furniture, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FurniturePojo catalog = catalogList.get(position);
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
        holder.linearL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty>0) {
                    FurniturePojo c = catalog;
                    Intent intent = new Intent(context, FurnitureDescriptionActivity
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

        TextView item1, txtStatus, textView9,textView22, textView1;
        ImageView imageButton1 , imb2,imb1 ;
        View view01,view02 ;
        Button btnView;
        boolean isPressed;
        LinearLayout linearL;


        public MyViewHolder(View itemView) {
            super(itemView);
            item1 = itemView.findViewById(R.id.item5);
            imageButton1 = itemView.findViewById(R.id.imageButton5);
            txtStatus =itemView.findViewById(R.id.txtStatus);
            linearL =itemView.findViewById(R.id.linearL);




//            //                        //FUEL ICON COLOR CHANGE
//            textView22.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isPressed) {
//                        textView22.setTextColor(Color.parseColor("#808080"));
//                        view02.setBackgroundColor(Color.parseColor("#808080"));
//                    } else {
//                        textView22.setTextColor(Color.parseColor("#ec669900"));
//                        view02.setBackgroundColor(Color.parseColor("#ec669900"));
//                    }
//                    isPressed = !isPressed; // reverse
//                }
//            });
//            textView15.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isPressed) {
//                        textView15.setTextColor(Color.parseColor("#808080"));
//                        view01.setBackgroundColor(Color.parseColor("#808080"));
//                    } else {
//                        textView15.setTextColor(Color.parseColor("#ec669900"));
//                        view01.setBackgroundColor(Color.parseColor("#ec669900"));
//                    }
//                    isPressed = !isPressed; // reverse
//                }
//            });
////                        //SPEED ICON COLOR CHANGE
//            imb1.setBackgroundResource(R.drawable.storageone);
//            imb1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isPressed) {
//                        textView1.setTextColor(Color.parseColor("#808080"));//gray
//                        view.setBackgroundResource(R.drawable.storageone);
//                    } else {
//                        textView1.setTextColor(Color.parseColor("#ec669900"));//orange
//                        view.setBackgroundResource(R.drawable.storagetwo);
//                    }
//                    isPressed = !isPressed; // reverse
//                }
//            });
//            imb2.setBackgroundResource(R.drawable.displaytwo);
//            imb2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isPressed) {
//                        textView9.setTextColor(Color.parseColor("#808080"));//gray
//                        view.setBackgroundResource(R.drawable.displaytwo);
//                    } else {
//                        textView9.setTextColor(Color.parseColor("#ec669900"));//orange
//                        view.setBackgroundResource(R.drawable.displayone);
//                    }
//                    isPressed = !isPressed; // reverse
//                }
//            });
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

                    ArrayList<FurniturePojo> filteredList = new ArrayList<>();

                    for (FurniturePojo androidVersion : catalogss) {

                        if (androidVersion.getTitle().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                            //System.out.println("Something found" + filteredList.size());


                        }
                        if (filteredList.size() == 0){
                            System.out.println("Nothing found....");
                            Toast.makeText(context, "Search result not Found", Toast.LENGTH_SHORT).show();
                        } else {
                            //System.out.println("Something found....");
                            Toast.makeText(context, "Search result Found", Toast.LENGTH_SHORT).show();

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
                catalogList = (ArrayList<FurniturePojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

