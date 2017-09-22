package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class LandingScreen extends AppCompatActivity {

    private ImageButton imCar,ibPhone,ibFurniture,ibLaptop,ibFAQs,ibProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
        imCar =(ImageButton)findViewById(R.id.imCar);
        ibPhone =(ImageButton)findViewById(R.id.ibPhone);
        ibLaptop =(ImageButton)findViewById(R.id.ibLaptop);
        ibFurniture =(ImageButton)findViewById(R.id.ibFurniture);
        ibFAQs =(ImageButton)findViewById(R.id.ibFAQs);
        ibProfile =(ImageButton)findViewById(R.id.ibProfile);
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
        ibFAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,ViewActivity.class);
                startActivity(intent);
            }
        });
        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingScreen.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


    }
    //SHOWS THE NOTIFICATION ICON ON THE XML OF THIS ACTIVITY
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.notification, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

