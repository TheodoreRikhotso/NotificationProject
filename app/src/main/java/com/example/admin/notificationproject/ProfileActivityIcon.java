package com.example.admin.notificationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivityIcon extends AppCompatActivity {

    ImageButton imProfile;
    TextView tvAboutUs, tvFAQ, TVContact,tvLogOut, tvUserName,tvUserEmail ;
    private FirebaseAuth auth;
    private LinearLayout llAbout,llContact,llLogout,llfaq;
    LinearLayout linearLayoutPro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_icon);

        //        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarSettings) ;
        toolbar.setTitle("Settings");
        toolbar.setTitleTextColor(Color.BLACK);



        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imProfile=(ImageButton)findViewById(R.id.imProfile);
        tvAboutUs=(TextView)findViewById(R.id.tvAboutUs);
        tvFAQ=(TextView)findViewById(R.id.tvFAQ);
        TVContact=(TextView)findViewById(R.id.TVContact);
        tvLogOut=(TextView)findViewById(R.id.tvLogOut);
        tvUserName=(TextView)findViewById(R.id.tvUserName);
        tvUserEmail=(TextView)findViewById(R.id.tvUserEmail);


        llAbout= (LinearLayout)findViewById(R.id.llAbout);
        llContact= (LinearLayout)findViewById(R.id.llContact);
        llLogout= (LinearLayout)findViewById(R.id.lllogout);
        llfaq = (LinearLayout)findViewById(R.id.llFaq);
        linearLayoutPro = (LinearLayout)findViewById(R.id.linearLayoutPro);



        //
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getUid()==null)
        {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Users/"+user.getUid()+"/Profile");

        auth = FirebaseAuth.getInstance();

        DatabaseReference buisnessAccRef = databaseUser.child(user.getUid());

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot!= null) {
                    ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);
                    if(person!= null) {
                        tvUserName.setText(person.getName());
                        tvUserEmail.setText(user.getEmail());




                        Glide.with(ProfileActivityIcon.this).load(person.getImage()).asBitmap().centerCrop().placeholder(R.drawable.profile_icon_).into(new BitmapImageViewTarget(imProfile) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ProfileActivityIcon.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imProfile.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivityIcon.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        linearLayoutPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivityIcon.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivityIcon.this,AboutImageActivity.class);
                startActivity(intent);
            }
        });
        llfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivityIcon.this,FAQ.class);
                startActivity(intent);
            }
        });
        llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivityIcon.this,ContactActivity.class);
                startActivity(intent);
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    auth.signOut();
                    if (auth.getCurrentUser() == null)
                    {
                        startActivity(new Intent(ProfileActivityIcon.this,loginsignup.class));
                        finish();
                    }
            }
        });

    }
}
