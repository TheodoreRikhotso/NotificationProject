package com.example.admin.notificationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Admin on 9/1/2017.
 */

public class NotificationAdapter extends BaseAdapter {
    private Context c;

    private int resource;
    private List<NotificationPojo> notificationPojos;
    private LayoutInflater inflater;

    public static String STORE = "";
    public  static double AVERAGE= 0;
    public static String COMMENT="";
    public static String NAME_SUBJECT="";
    private double pertotal= 0;


    public NotificationAdapter(Context c, List<NotificationPojo> reports) {
        this.c = c;
        this.notificationPojos = reports;
    }


    @Override
    public int getCount() {
        return notificationPojos.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationPojos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View rootView, ViewGroup parent) {

        if (inflater == null) {

            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        }
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.model, parent, false);
        }


        NotificationPojo notificationPojo = notificationPojos.get(position);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        TextView tvMsg = (TextView) rootView.findViewById(R.id.tvMsg);
        TextView tvDate = (TextView) rootView.findViewById(R.id.tvDate);

        ImageView ivIconNoti =rootView.findViewById(R.id.ivIconNoti);


        tvDate.setText(notificationPojo.getDate());
        tvMsg.setText(notificationPojo.getMessage());
        tvTitle.setText(notificationPojo.getTitle());
        Glide.with(c)
                .load(notificationPojo.getImageUrl())
                .into(ivIconNoti);






        return rootView;
    }


}
