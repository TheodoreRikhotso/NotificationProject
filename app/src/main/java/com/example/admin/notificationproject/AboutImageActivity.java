package com.example.admin.notificationproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AboutImageActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private GridView lv;
    TextView tvAbout;
    private ImageListAdapter adapter;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    // private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_image);

        //SETS THE SLIDING VIEWPAGER
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);
        dotscount = viewPageAdapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));


        //        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAbout);
        toolbar.setTitle("About Mlab");
        toolbar.setTitleTextColor(Color.BLACK);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        toolbar.setTitleTextColor(Color.BLACK);

        imgList = new ArrayList<>();
        tvAbout = (TextView) findViewById(R.id.tvAbout);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 1000000);
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            AboutImageActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
            //END OF VIEW PAGER AND SLIDING DOTS
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("images");
            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String value = dataSnapshot.getValue(String.class);
                    tvAbout.setText(value);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            mDatabaseRef = FirebaseDatabase.getInstance().getReference(AboutsActivity.FB_DATABASE_PATH);
            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                progressDialog.dismiss();
                    //Fetch image data from firebase database
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //ImageUpload class require default constructor
                        ImageUpload img = snapshot.getValue(ImageUpload.class);
                        imgList.add(img);
                    }
//
//                //Init adapter
//                adapter = new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imgList);
//                //Set adapter for listview
//                lv.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
//                progressDialog.dismiss();
                }
            });


            //lv = (GridView) findViewById(R.id.listViewImage);
            //Show progress dialog during list image loading
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait loading list image...");
//        progressDialog.show();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("images");

            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String value = dataSnapshot.getValue(String.class);
                    tvAbout.setText(value);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabaseRef = FirebaseDatabase.getInstance().getReference(AboutsActivity.FB_DATABASE_PATH);

            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                progressDialog.dismiss();

                    //Fetch image data from firebase database
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //ImageUpload class require default constructor
                        ImageUpload img = snapshot.getValue(ImageUpload.class);
                        imgList.add(img);
                    }

//
//                //Init adapter
//                adapter = new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imgList);
//                //Set adapter for listview
//                lv.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

//                progressDialog.dismiss();
                }
            });

        }
    }
}

