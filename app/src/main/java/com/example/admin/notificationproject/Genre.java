package com.example.admin.notificationproject;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Admin on 04-Oct-17.
 */

public class Genre extends ExpandableGroup {
    public Genre(String title, List items) {
        super(title, items);
    }
}
