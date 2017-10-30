package com.example.admin.notificationproject;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Admin on 10/2/2017.
 */

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.MyViewHolder> {
    private Activity context;
    private List<UserItemPojo> userItemPojos;
    private Activity applicationContext;
    private boolean check =false;

    public UserItemAdapter(Activity context, List<UserItemPojo> userItemPojos) {
        this.context = context;
        this.userItemPojos = userItemPojos;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_model,null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final UserItemPojo userItemPojo = userItemPojos.get(position);
        holder.tvItemDescr.setText(userItemPojo.getName()+"\n"+userItemPojo.getItemDate()+"\n"+userItemPojo.getItemTime());
        holder.tvItemDescr.setVisibility(View.GONE);
        Glide.with(context)
                .load(userItemPojo.getImageUri())
                .into(holder.ibItem);
        holder.tvItemDescr.startAnimation(AnimationUtils.loadAnimation(context, R.anim.text));


        holder.ibItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UserItemPojo c = userItemPojo;
//                Intent intent = new Intent(context,DescriptionActivity
//                        .class);
//                intent.putExtra("select", c);
//                context.startActivity(intent);
                //holder.tvItemDescr.setLines(3);
                if(check==false) {
                    holder.tvItemDescr.setVisibility(View.VISIBLE);
                    check=true;
                }else {
                    holder.tvItemDescr.setVisibility(View.GONE);
                    check=false;
                }




            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != userItemPojos ? userItemPojos.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemDescr;
        ImageButton ibItem;



        public MyViewHolder(View itemView) {
            super(itemView);
            tvItemDescr = itemView.findViewById(R.id.tvItemDescr);
            ibItem = itemView.findViewById(R.id.ibItem);

        }
    }
}

