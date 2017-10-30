package com.example.admin.notificationproject;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by Admin on 04-Oct-17.
 */

public class GenreViewHolder extends GroupViewHolder {

    private TextView genreTitle;

    public GenreViewHolder(View itemView) {
        super(itemView);
        genreTitle = (TextView)itemView.findViewById(R.id.list_item_genre_name);


    }

    public void setGenreName(String name){
        genreTitle.setText(name);
    }
}
