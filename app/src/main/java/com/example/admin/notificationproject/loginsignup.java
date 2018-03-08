package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class loginsignup extends AppCompatActivity {
    TextView signin;
//    TextView signup;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static String CHECK="NON";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsignup);

        auth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(getApplicationContext(), LandingScreen.class));

                }else {
                    startActivity(new Intent(getApplicationContext(), loginsignup.class));
                }

            }
        };
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), LandingScreen.class));
        }
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LandingScreen.class));

        }
        signin = (TextView)findViewById(R.id.signin);
//        signup = (TextView)findViewById(R.id.signup);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(loginsignup.this,LoginActivity.class);
                startActivity(it);
            }
        });


//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent it = new Intent(loginsignup.this,SignUpActivity.class);
//                startActivity(it);
//
//            }
//        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();

        }
        return true;
    }


}
