package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class QAndA extends AppCompatActivity {
    TextView ename,eemail,eaddress;
    Button save,view;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Listdata> list;
    RecyclerView recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand);




        // ename = (TextView) findViewById(R.id.etname);
        eemail = (TextView) findViewById(R.id.eemail);
        eaddress = (TextView) findViewById(R.id.eaddress);
        save = (Button) findViewById(R.id.save);
        view = (Button) findViewById(R.id.view);
        recyclerview = (RecyclerView) findViewById(R.id.rview);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String name =  ename.getText().toString();
                String email = eemail.getText().toString();
                String address = eaddress.getText().toString();


                String key = myRef.push().getKey();
                Userdetails userdetails = new Userdetails();

                // userdetails.setName(name);
                userdetails.setEmail(email);
                userdetails.setAddress(address);

                myRef.child(key).setValue(userdetails);
                //who are you?    ename.setText("");
                eemail.setText("");
                eaddress.setText("");

            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QAndA.this, ViewActivity.class);
                startActivity(intent);


            }
        });
    }
}
