package com.example.admin.notificationproject;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Admin on 8/30/2017.
 */

public class MyFirebaseIntanceIdService extends FirebaseInstanceIdService {

    private static final String TAG="MyFirebaseIntance";
    @Override
    public void onTokenRefresh() {
//get upldate
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token: "+refreshToken);
    }
}
