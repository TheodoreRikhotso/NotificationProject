package com.example.admin.notificationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingScreen extends AppCompatActivity {
    private FirebaseAuth auth;
    private ImageButton imCar,ibPhone,ibFurniture,ibLaptop,notification_panel, ibFAQs,ibProfile;
private CircleImageView profile;
    public static String ACYIVITY=" non";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//ACTIONBAR
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4D000000")));
//        getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        super.onCreate(savedInstanceState);

        SignUpActivity.CONTEXT ="LandingScreen";


        setContentView(R.layout.activity_landing_screen);


        Toolbar toolbar = (Toolbar)findViewById(R.id.tbLanding);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

       imCar =(ImageButton)findViewById(R.id.imCar);
        ibPhone =(ImageButton)findViewById(R.id.ibPhone);
        ibLaptop =(ImageButton)findViewById(R.id.ibLaptop);
        ibFurniture =(ImageButton)findViewById(R.id.ibFurniture);
        profile =(CircleImageView)findViewById(R.id.profile);
        notification_panel =(ImageButton)findViewById(R.id.notification_panel);
//        ibFAQs =(ImageButton)findViewById(R.id.ibFAQs);
//        ibProfile =(ImageButton)findViewById(R.id.ibProfile);
        //logout
        auth = FirebaseAuth.getInstance();
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Profiles");

        DatabaseReference buisnessAccRef = databaseUser.child(users.getUid());

        buisnessAccRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot!= null) {
                    ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);
                    if(person!= null) {



                        Glide.with(LandingScreen.this).load(person.getImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(profile) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(LandingScreen.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                profile.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LandingScreen.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        imCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,CarActivity.class);
                startActivity(intent);
            }
        });
        ibPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,PhoneActiviy.class);
                startActivity(intent);
            }
        });

        ibLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,LaptopActivity.class);
                startActivity(intent);
            }
        });

        ibFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,FurnitureActivity.class);
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        notification_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
//        ibFAQs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LandingScreen.this,FAQ.class);
//                startActivity(intent);
//            }
//        });
//        ibProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LandingScreen.this,ProfileActivity.class);
//                startActivity(intent);
//            }
//        });


    }


    //SHOWS THE NOTIFICATION ICON ON THE XML OF THIS ACTIVITY


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dots, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent intent = new Intent(LandingScreen.this,AboutImageActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_help:
                Intent intentR = new Intent(LandingScreen.this,FAQ.class);
                startActivity(intentR);
                return true;
            case R.id.menu_logout:
                logoutUser();

            case R.id.  menu_contact:
                Intent intentt = new Intent(LandingScreen.this,ContactActivity.class);
                startActivity(intentt);
                return true;
            case R.id.notification_icon:
                Intent intents = new Intent(LandingScreen.this,NotificationActivity.class);
                startActivity(intents);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void logoutUser() {
        auth.signOut();
        if (auth.getCurrentUser() == null)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }
}

