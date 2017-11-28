package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordAcitivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email;
    private Button btnResetPass;
    private Button btnBack;
    private RelativeLayout activity_forgot;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_acitivity);

//toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.sentmail);
        toolbar.setTitle(" ");


        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_sign));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //view
        input_email = (EditText) findViewById(R.id.forgot_email);
        btnResetPass = (Button) findViewById(R.id.forgot_btn_reset);
//        btnBack = (Button) findViewById(R.id.forgot_btn_back);
        activity_forgot = (RelativeLayout) findViewById(R.id.activity_main);

        btnResetPass.setOnClickListener(this);
//        btnBack.setOnClickListener(this);

        //init Firebase
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.forgot_btn_back)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else if (view.getId() == R.id.forgot_btn_reset)
        {
            String mail =input_email.getText().toString();
            if(mail.isEmpty()) {
                input_email.setError("Please Enter Email");

            }else
            {
                resetPassword(input_email.getText().toString());
            }
        }

    }


    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar snackBar = Snackbar.make(activity_forgot,"We have sent password to email: "+email,Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }
                        else {
                            Snackbar snackBar = Snackbar.make(activity_forgot,"Failed to send password",Snackbar.LENGTH_SHORT);
                            snackBar.show();

                        }

                    }
                });
    }
}

