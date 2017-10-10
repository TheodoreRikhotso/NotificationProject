package com.example.admin.notificationproject;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by Admin on 04-Oct-17.
 */

public class ArtistViewHolder extends ChildViewHolder {
    private TextView artistName;

    public ArtistViewHolder(View itemView) {
        super(itemView);
        artistName = (TextView)itemView.findViewById(R.id.list_item_artist_name);

    }

    public void setArtistName(String name){
        artistName.setText(name);
    }
}
