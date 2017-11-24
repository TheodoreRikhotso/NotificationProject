package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtWelcome;

    private Button btnChangePass, btnLogout;
    private RelativeLayout activity_dashboard;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        //view
        txtWelcome = (TextView) findViewById(R.id.dashboard_welcome);

        btnLogout = (Button) findViewById(R.id.dashboard_btn_logout);
        activity_dashboard= (RelativeLayout) findViewById(R.id.activity_dash_board);

        //btnChangePass.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
       // input_new_password.setVisibility(View.GONE);
        //init Firebase
        auth = FirebaseAuth.getInstance();

        //session check
        if(auth.getCurrentUser() !=null)
            txtWelcome.setText("");
    }

    @Override
    public void onClick(View view) {
       if (view.getId() == R.id.dashboard_btn_logout)
            logoutUser();

    }

    private void logoutUser() {
        auth.signOut();
        if (auth.getCurrentUser() == null)
        {
            startActivity(new Intent(DashBoardActivity.this,LoginActivity.class));
            finish();
        }
    }

    private void changePassword(String newPassword) {
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Snackbar snackBar = Snackbar.make(activity_dashboard,"Password changed", Snackbar.LENGTH_SHORT);
                    snackBar.show();
                }

            }
        });

    }
}

