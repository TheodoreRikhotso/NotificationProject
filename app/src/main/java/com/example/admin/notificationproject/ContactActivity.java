package com.example.admin.notificationproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ContactActivity extends AppCompatActivity {

    TextView ename,eemail,eaddress;
    Button save,view;
    List<Listdata> list;
    RecyclerView recyclerview;

    DatabaseReference myRef;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tbContact) ;
        toolbar.setTitle("Contact Us");
        toolbar.setTitleTextColor(Color.BLACK);


        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference("Users/"+user.getUid()+"/Message");


        // ename = (TextView) findViewById(R.id.etname);
        eemail = (TextView) findViewById(R.id.eemail);
        eaddress = (TextView) findViewById(R.id.eaddress);
        ename = (TextView) findViewById(R.id.edName);
        save = (Button) findViewById(R.id.save);

        if(user!=null) {
            eemail.setText(" " + user.getEmail());


            DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Users/"+user.getUid()+"/Profile");



            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot != null) {
                        ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);
                        if (person != null) {
                            ename.setText(" " + person.getName());

                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ContactActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        recyclerview = (RecyclerView) findViewById(R.id.rview);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String name =  ename.getText().toString();
                String email =  eemail.getText().toString();
                String address =  eaddress.getText().toString();
                String name =  eaddress.getText().toString();



                    String key = myRef.push().getKey();
                    UserContact userdetails = new UserContact();

                    // userdetails.setName(name);
                    userdetails.setEmail(email);
                    userdetails.setAddress(address);
                    userdetails.setName(name);

                    myRef.child(key).setValue(userdetails);
                    //who are you?    ename.setText("");
                    eemail.setText("");
                    eaddress.setText("");
                    ename.setText("");
                    Toast.makeText(ContactActivity.this, "Message sent ", Toast.LENGTH_SHORT).show();



            }
        });











    }
}
