package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends ActionBarActivity {
private static final  String TAG="MainActivity";
    Button btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow = (Button)findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
//                Notification noti = new Notification.Builder(MainActivity.this)
//                        .setTicker("TickerTitle")
//                        .setContentTitle("New Car")
//                        .setContentText("New car has just been added")
//                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
//                        .setContentIntent(pIntent).getNotification();
//                noti.flags = Notification.FLAG_AUTO_CANCEL;
//                NotificationManager nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                nm.notify(0,noti);
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG,"Token: "+token);
                Toast.makeText(MainActivity.this,token,Toast.LENGTH_LONG).show();


            }
        });

    }
}
