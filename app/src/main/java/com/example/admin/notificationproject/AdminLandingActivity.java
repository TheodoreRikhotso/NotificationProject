package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminLandingActivity extends AppCompatActivity {

    private Button btnAddData,btnViewData,btnUpdateData,btnDelete,btnAssetBooked,btnContactUs,btnUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);

        btnAddData = (Button) findViewById(R.id.btnAddDataAdmin);
        btnViewData = (Button) findViewById(R.id.btnViewData);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnAssetBooked = (Button) findViewById(R.id.btnAssetBooked);
        btnContactUs = (Button) findViewById(R.id.btnContactUs);
        btnUser =(Button)findViewById(R.id.btnUsers);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLandingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLandingActivity.this,CatalogActivity.class);
                startActivity(intent);
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLandingActivity.this,UsersActivity.class);
                startActivity(intent);
            }
        });
    }
}
