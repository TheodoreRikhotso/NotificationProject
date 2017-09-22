package com.example.admin.notificationproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 8/30/2017.
 */

public class MyFirebaseMassagingService extends FirebaseMessagingService {
    private static final String TAG ="MyFirebaseMassaging";
private String title,message,img_url;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static String Name;
    private byte[] data;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG," FROM: "+remoteMessage);


        if(remoteMessage.getData().size()>0)
        {
                Log.d(TAG,"Message data: "+remoteMessage.getData());
        }
        if(remoteMessage.getNotification()!=null)
        {
            Log.d(TAG,"Message body: "+remoteMessage.getData());

            Date currentTime =  Calendar.getInstance().getTime();

            sendNotification(CatalogAdminActivity.NOTIFY,CatalogAdminActivity.TYPE);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());




        }
    }

    private void sendNotification(Catalog catalog,String title) {

        InputStream stream = null;
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        final NotificationCompat.Builder notificationBuilder =new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(catalog.assetTitle+" Just add"+catalog.getDescription());
        notificationBuilder.setSound(noti);

       
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);


        notificationBuilder.addAction(R.drawable.ic_remove_red_eye_black_24dp,"View",pendingIntent);
        notificationBuilder.addAction(R.drawable.ic_visibility_off_black_24dp,"Ignore",pendingIntent);

        Bitmap bitmap = getBitmapFromURL(catalog.catalogimageurl);
        notificationBuilder.setLargeIcon(bitmap);
        NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build()) ;


    }
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
