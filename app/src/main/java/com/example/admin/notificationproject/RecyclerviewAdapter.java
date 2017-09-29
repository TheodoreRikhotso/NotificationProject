package com.example.admin.notificationproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 9/21/2017.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> {

    List<Listdata> listdata;

    public RecyclerviewAdapter(List<Listdata> listdata) {
        this.listdata = listdata;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.q_and_a_model, parent, false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        Listdata data = listdata.get(position);
//            holder.vname.setText(data.getName());
        holder.vemail.setText(data.getEmail());
        holder.vaddress.setText(data.getAddress());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView vname, vaddress, vemail;

        public MyHolder(View itemView) {
            super(itemView);
            //  vname = (TextView) itemView.findViewById(R.id.vname);
            vemail = itemView.findViewById(R.id.vemail);
            vaddress = itemView.findViewById(R.id.vaddress);

        }
    }
}
