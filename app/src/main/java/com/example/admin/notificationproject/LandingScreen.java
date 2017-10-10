package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class LandingScreen extends AppCompatActivity {
    private FirebaseAuth auth;
    private ImageButton imCar,ibPhone,ibFurniture,ibLaptop,profile,notification_panel, ibFAQs,ibProfile;

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
        profile =(ImageButton)findViewById(R.id.profile);
        notification_panel =(ImageButton)findViewById(R.id.notification_panel);
//        ibFAQs =(ImageButton)findViewById(R.id.ibFAQs);
//        ibProfile =(ImageButton)findViewById(R.id.ibProfile);
        //logout
        auth = FirebaseAuth.getInstance();

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

