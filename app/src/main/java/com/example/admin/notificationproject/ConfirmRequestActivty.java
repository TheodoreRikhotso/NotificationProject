package com.example.admin.notificationproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class ConfirmRequestActivty extends AppCompatActivity {
    RingProgressBar ringProgressBar1, ringProgressBar2;
    TextView tvDay, Loading;
    ImageView ivIcon;

    int progress = 0;
    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0)
            {
                if(progress<100)
                {
                    progress++;
                    // ringProgressBar1.setProgress(progress);
                    ringProgressBar2.setProgress(progress);

                }
            }
        }
    };




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_confirm_request_activty);


            //DIALOG BOX INITIALIZATION
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ConfirmRequestActivty.this);
            View mView = getLayoutInflater().inflate(R.layout.activity_confirm_request_activty, null);


            tvDay = mView.findViewById(R.id.tvDay);
            ivIcon = mView.findViewById(R.id.ivIcon);
            ringProgressBar2 = mView.findViewById(R.id.progress_bar_2);
            Loading = mView.findViewById(R.id.Loading);



            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i<100; i++){
                        try{
                            Thread.sleep(50);
                            myHandler.sendEmptyMessage(0);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvDay.setVisibility(View.VISIBLE);
                            ivIcon.setVisibility(View.VISIBLE);
                            Loading.setVisibility(View.GONE);

                        }
                    });
                }
            }).start();


            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
            //DIALOG END

        }

    }

