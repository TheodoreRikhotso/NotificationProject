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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 19-Oct-17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder>   implements Filterable {


    private Activity context;
    private List<ProfilePojo> catalogList, catalogss;
    private Activity applicationContext;
    private Laptop catalog;

    public UsersAdapter(Activity context, List<ProfilePojo> catalogList) {
        this.context = context;
        this.catalogList = catalogList;

        this.catalogss = catalogList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_model, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ProfilePojo catalog = catalogList.get(position);
        holder.tvStuff_No.setText(catalog.getStuffNo());
        holder.tvName.setText(catalog.getName());
        holder.tvDepartmentName.setText(catalog.getDepartmentName());

        Glide.with(context)
                .load(catalog.getImage())
                .into(holder.imageButton1);


        holder.imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfilePojo c = catalog;
                Intent intent = new Intent(context, LaptopDescriptionActivity
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
        TextView item3;
LinearLayout llUserItem;
        TextView item1, tvStuff_No, tvName,tvDepartmentName, textView1;
        ImageView imageButton1 , imb2,imb1 ;
        View view01,view02 ;
        Button btnView;
        boolean isPressed;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvStuff_No = itemView.findViewById(R.id.tvStuff_No);
            tvName = itemView.findViewById(R.id.tvName);
            tvDepartmentName = itemView.findViewById(R.id.tvDepartmentName);
            imageButton1 = itemView.findViewById(R.id.ivUserImage);



            //                        //FUEL ICON COLOR CHANGE
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
//            imb1.setBackgroundResource(R.drawable.wheels01);
//            imb1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isPressed) {
//                        textView1.setTextColor(Color.parseColor("#808080"));//gray
//                        view.setBackgroundResource(R.drawable.wheels01);
//                    } else {
//                        textView1.setTextColor(Color.parseColor("#ec669900"));//orange
//                        view.setBackgroundResource(R.drawable.wheels02);
//                    }
//                    isPressed = !isPressed; // reverse
//                }
//            });
//            imb2.setBackgroundResource(R.drawable.hard_drive1);
//            imb2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isPressed) {
//                        textView9.setTextColor(Color.parseColor("#808080"));//gray
//                        view.setBackgroundResource(R.drawable.hard_drive1);
//                    } else {
//                        textView9.setTextColor(Color.parseColor("#ec669900"));//orange
//                        view.setBackgroundResource(R.drawable.hard_drive2);
//                    }
//                    isPressed = !isPressed; // reverse
//                }
//            });
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

                    ArrayList<ProfilePojo> filteredList = new ArrayList<>();

                    for (ProfilePojo androidVersion : catalogss) {

                        if (androidVersion.getName().toLowerCase().contains(charString)) {

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
                catalogList = (ArrayList<ProfilePojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}


