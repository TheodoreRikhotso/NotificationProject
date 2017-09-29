package com.example.admin.notificationproject;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Admin on 9/22/2017.
 */

public class ImageListAdapter  extends ArrayAdapter<ImageUpload> {
    private Activity context;
    private int resource;
    private List<ImageUpload> listImage;

    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        TextView description = (TextView) v.findViewById(R.id.description);
        TextView descriptions = (TextView) v.findViewById(R.id.descriptions);

        tvName.setText(listImage.get(position).getName());
        description.setText(listImage.get(position).getDescription());
        descriptions.setText(listImage.get(position).getDescriptions());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        return v;

    }

}
