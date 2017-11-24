package com.example.admin.notificationproject;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

class ViewPageAdpter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.rulani_nevhufumba, R.drawable.lebogang_madise, R.drawable.khuthadzo_gundula,R.drawable.melvin_musehani, R.drawable.sihle_mabaleka,R.drawable.veronica, R.drawable.tendai_mazhude,
            R.drawable.alexandrafraser, R.drawable.zanoxolo_mngadi, R.drawable.motebang_mokwatsi, R.drawable.tinga, R.drawable.derrick_kotze };

    private String [] person = {"Rulani Nevhufumba", "Lebogang Madise", "Khuthadzo Gundula", "Melvin Musehani", "Sihle Mabaleka", "Veronica Mahlangu", "Tendai Mazhude", "Alexandra Fraser", "Zanoxolo Mngadi", "Motebang Mokwatsi",
            "Zakhele Tinga","Derrick Kotze"};

    private String [] area = {"mLab Provincial Manager | Gauteng", "mLab Provincial Manager | Western Cape & DEMOLA SA Lead", "CodeTribe Facilitator Tshwane | Android Developer", "mLab Developer in Residence",
            "Facilitator CodeTribe Soweto | DEMOLA Johannesburg", "mLab & CodeTribe Coordinator", "Operations and M&E Consultant", "Partnerships & Ecosystem Consultant", "Facilitator CodeTribe Alexandra",
            "Facilitator CodeTribe Tembisa", "Facilitation and Coding Assistant Soweto", "Chief Executive Officer"};


    public ViewPageAdpter(Context context){
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

