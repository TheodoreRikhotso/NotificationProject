package com.example.admin.notificationproject;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Admin on 04-Oct-17.
 */

public class Title extends ExpandableGroup<Listdata> {
    @SuppressLint("ParcelCreator")
    public Title(String title, List<Listdata> items) {
        super(title, items);
    }

    public Title(Parcel in) {
        super(in);
    }
}
