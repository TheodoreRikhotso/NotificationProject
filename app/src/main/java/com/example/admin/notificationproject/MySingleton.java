package com.example.admin.notificationproject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Admin on 8/31/2017.
 */

public class MySingleton {
    private static MySingleton mInstance;
    private static Context context;
    private RequestQueue requestQueue;

    public MySingleton(Context context) {
        this.context=context;
        this.requestQueue = requestQueue;
    }

    private RequestQueue getRequestQueue()
    {
        if(requestQueue ==null)
        {
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getmInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new MySingleton((context));
        }
        return  mInstance;
    }

    public <T>void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }

}
