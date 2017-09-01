package com.example.admin.notificationproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
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
            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
            img_url = remoteMessage.getData().get("img_url");
            Date currentTime =  Calendar.getInstance().getTime();
            sendNotification(message,title,img_url);
            TitlePojo titlePojo = new TitlePojo();
            titlePojo.setTitle(title);
            titlePojo.setMessage(message);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());
            titlePojo.setDate(currentDateandTime);

            Database database = new Database(this);
            database.addContact(titlePojo);

        }
    }

    private void sendNotification(String body,String tiltes,String url) {

        InputStream stream = null;
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        final NotificationCompat.Builder notificationBuilder =new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(tiltes);
        notificationBuilder.setContentText(body);
        notificationBuilder.setSound(noti);

       
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);


        notificationBuilder.addAction(R.drawable.ic_remove_red_eye_black_24dp,"View",pendingIntent);
        notificationBuilder.addAction(R.drawable.ic_visibility_off_black_24dp,"Ignore",pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.ic_notifications_active_black_24dp);
//        ImageRequest imageRequest = new ImageRequest(image, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                notificationBuilder.setStyle( new  NotificationCompat.BigPictureStyle().bigPicture(response));
//
//            }
//        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
        SharedPreferences.Editor editor = sharedpreferences.edit();
       // MySingleton.getmInstance(this).addToRequestQue(imageRequest);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor.commit();
        NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build()) ;


    }

}
