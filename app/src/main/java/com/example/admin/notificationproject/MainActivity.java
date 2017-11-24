package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
private static final  String TAG="MainActivity";
    Button btnShow;
    Button LaptopAdmin,PhonesAdmin, CarsAdmin, FurnitureAdmin  ;
    public static String CATA="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);


//        btnShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent();
////                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
////                Notification noti = new Notification.Builder(MainActivity.this)
////                        .setTicker("TickerTitle")
////                        .setContentTitle("New Car")
////                        .setContentText("New car has just been added")
////                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
////                        .setContentIntent(pIntent).getNotification();
////                noti.flags = Notification.FLAG_AUTO_CANCEL;
////                NotificationManager nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
////                nm.notify(0,noti);
//                String token = FirebaseInstanceId.getInstance().getToken();
//                Log.d(TAG,"Token: "+token);
//                Toast.makeText(MainActivity.this,token,Toast.LENGTH_LONG).show();
//
//
//            }
//        });





            LaptopAdmin = (Button)findViewById(R.id.Laptop_Admin);
            PhonesAdmin = (Button)findViewById(R.id.PhonesAdmin);
            CarsAdmin = (Button)findViewById(R.id.CarsAdmin);
            FurnitureAdmin = (Button)findViewById(R.id.FurnitureAdmin);


//        LaptopAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CATA="laptop";
//                    Intent intent = new Intent(getApplicationContext(), CatalogAdminActivity.class);
//                    startActivity(intent);
//                }
//            });



//            LaptopAdmin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    CATA="laptop";
//                    Intent intent = new Intent(getApplicationContext(), CatalogAdminActivity.class);
//                    startActivity(intent);
//                }
//            });
            PhonesAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CATA="phone";
                    Intent intent = new Intent(getApplicationContext(), CatalogAdminActivity.class);
                    startActivity(intent);

                }
            });
            CarsAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CATA="car";
                    Intent intent = new Intent(getApplicationContext(), CatalogAdminActivity.class);
                    startActivity(intent);

                }
            });
            FurnitureAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CATA="furniture";
                    Intent intent = new Intent(getApplicationContext(), CatalogAdminActivity.class);
                    startActivity(intent);

                }
            });
        }

    }

