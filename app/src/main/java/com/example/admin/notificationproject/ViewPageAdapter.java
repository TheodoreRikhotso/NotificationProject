package com.example.admin.notificationproject;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

class ViewPageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.nonhlanhla_m, R.drawable.theo, R.drawable.rob,R.drawable.khumbudzo, R.drawable.nathi0};

    private String [] person = {"Nonhlanhla Mkhonza", "Theodore Paseka  Rikhotso", "Lesego Mohalanyane", "Kumbudzo Ramulifho", "Nkosinathi Madlophe"};

    private String [] area = {"https://www.linkedin.com/in/nonhlanhla-su-zaan-mkonza-32226772", "https://www.linkedin.com/in/paseka-rikhotso-ba0b84113/", "https://www.linkedin.com/in/lesego-thato-846601159/", "https://www.linkedin.com/in/khumbudzo-rmulifho-255a87159/",
            "https://www.linkedin.com/in/de-nathi-madlophe-561589159/"};


    public ViewPageAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.about_slide_layout, null);
        CircleImageView imageSlider = view.findViewById(R.id.imageSlider);
        imageSlider.setImageResource(images[position]);

        //TEXTVIEWS
        TextView tvPerson = view.findViewById(R.id.tvPerson);
        TextView tvPosition  = view.findViewById(R.id.tvPosition);
        tvPerson.setText(" " +person[position]);
        tvPosition.setText(" " +area[position]);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view,0);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}

