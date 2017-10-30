package com.example.admin.notificationproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class LaptopDescriptionActivity extends AppCompatActivity {

    VerticalStepView verticalStepView;
    ImageView imageView;
    TextView tvRam, tvStorage, tvOs, tvGraphics;
    Button btnRequest;
    Button btn_red,btn_blue,btn_gray,btn_white,btn_black,btnEnter;
    private String image,name,id;
    private DatabaseReference databaseUserItem;
    private StorageReference mStorageReference;
    private FirebaseUser user;
    private  Laptop c;
    private int quantity;
    private Laptop person;
    private Boolean check = false;


    RingProgressBar ringProgressBar1, ringProgressBar2;
    TextView tvDay, Loading;
    ImageView ivIcon;
    Button btnOk;
    private DatabaseReference databaseProfile;

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
        setContentView(R.layout.activity_laptop_description);

        verticalStepView = (VerticalStepView)findViewById(R.id.vStV);

        Intent intent = getIntent();
        c = (Laptop)intent.getSerializableExtra("select");

        Toolbar toolbar = (Toolbar)findViewById(R.id.tbDescrption) ;
        toolbar.setTitle(c.getTitle());
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.DKGRAY);


        tvRam = (TextView) findViewById(R.id.tvRam);
        tvStorage = (TextView) findViewById(R.id.tvStorage);
        tvOs = (TextView) findViewById(R.id.tvOs);
        tvGraphics = (TextView) findViewById(R.id.tvGraphics);




        imageView = (ImageView)findViewById(R.id.imageView);
        btnRequest = (Button)findViewById(R.id.btnRequest);

        //usser item requested

        databaseUserItem = FirebaseDatabase.getInstance().getReference("UserItems");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        id = user.getUid();

        databaseProfile = FirebaseDatabase.getInstance().getReference("Profiles");


        databaseUserItem = FirebaseDatabase.getInstance().getReference("UserItems");
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ///DIALOG BOX INITIALIZATION
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LaptopDescriptionActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_confirm_request_activty, null);

                btnOk = mView.findViewById(R.id.btnOk);
                tvDay = mView.findViewById(R.id.tvDay);
                ivIcon = mView.findViewById(R.id.ivIcon);

                //Date, department

                Date currentTimes = Calendar.getInstance().getTime();
                DateFormat dateFormats = new SimpleDateFormat("dd MMM yyyy");
                //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));

                TextView tvDate=mView.findViewById(R.id.tvDates);
                final TextView tvDepartment=mView.findViewById(R.id.tvDert);
                final TextView tvType=mView.findViewById(R.id.tvType);
                tvDate.setText(dateFormats.format(currentTimes));

                ringProgressBar2 = mView.findViewById(R.id.progress_bar_2);
                Loading = mView.findViewById(R.id.Loading);

                //
                DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Profiles");

                DatabaseReference buisnessAccRef = databaseUser.child(user.getUid());

                buisnessAccRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot!= null) {
                            ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);
                            if(person!= null) {
                                tvDepartment.setText(person.getDepartmentName());
                                tvType.setText(LandingScreen.ACYIVITY);

                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(LaptopDescriptionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        UserItemPojo userItemPojo = new UserItemPojo();
                        userItemPojo.setName(name);
                        userItemPojo.setImageUri(image);
                        userItemPojo.setRefId(id);


                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                        Date currentLocalTime = cal.getTime();
                        DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
                        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

                        String localTime = date.format(currentLocalTime);
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//                LocalDateTime now = LocalDateTime.now();
                        userItemPojo.setItemDate(dateFormat.format(currentTime));
                        userItemPojo.setItemTime(localTime);

                        String userId = databaseUserItem.push().getKey();

                        databaseUserItem.child(userId).setValue(userItemPojo);

                        // Quantity
                        final DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Laptops");

                        final DatabaseReference buisnessAccRef = databaseUser.child(c.getId());
                        buisnessAccRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null) {

                                    person = dataSnapshot.getValue(Laptop.class);
                                    if (check == false)
                                    {
                                        check =true;
                                        String qty = person.getTotalQuantity();
                                        quantity = Integer.parseInt(qty);

                                        String q = String.valueOf(quantity - 1);

                                        person.setTotalQuantity(q);
                                        databaseUser.child(person.getId()).setValue(person);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Intent intents = new Intent(LaptopDescriptionActivity.this,LandingScreen.class);
                        startActivity(intents);
                    }
                });




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
        });







//        Toast.makeText(this, c.getContent1(), Toast.LENGTH_SHORT).show();
        tvRam.setText(c.getRam());
        tvGraphics.setText(c.getGraphics());
        tvOs.setText(c.getOs());
        tvStorage.setText(c.getStorage());

        name=c.getTitle();

//

//        Color1.setText(c.getColor1());
//        Color2.setText(c.getColor2());
//        Color3.setText(c.getColor3());
//        Color4.setText(c.getColor4());
//        Color5.setText(c.getColor5());

        btn_red = (Button)findViewById(R.id.btn_red);
        btn_black = (Button)findViewById(R.id.btn_black);
        btn_gray = (Button)findViewById(R.id.btnGrey);



        btn_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_black.setBackground(getDrawable(R.drawable.white_selected_button));
                btn_black.setScaleX((float) 1.3);
                btn_black.setScaleY((float) 1.3);

                btn_red.setScaleX( 1);
                btn_red.setScaleY( 1);

                btn_gray.setScaleX(1);
                btn_gray.setScaleY(1);


                if(c.getImage()!=null) {
                    Glide.with(getApplicationContext())
                            .load(c.getImage())
                            .into(imageView);
                }else {
                    Toast.makeText(LaptopDescriptionActivity.this, "Color not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });



        btn_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_gray.setBackground(getDrawable(R.drawable.black_selected_button));
                btn_gray.setScaleX((float) 1.3);
                btn_gray.setScaleY((float) 1.3);

                btn_black.setScaleX( 1);
                btn_black.setScaleY( 1);




                btn_red.setScaleX(1);
                btn_red.setScaleY(1);

                if(c.getImage1()!=null) {
                    Glide.with(getApplicationContext())
                            .load(c.getImage1())
                            .into(imageView);
                }else {
                    Toast.makeText(LaptopDescriptionActivity.this, "Color not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_red.setBackground(getDrawable(R.drawable.blue_selected_button));
                btn_red.setScaleX((float) 1.3);
                btn_red.setScaleY((float) 1.3);



                btn_gray.setScaleX(1);
                btn_gray.setScaleY(1);



                btn_black.setScaleX(1);
                btn_black.setScaleY(1);


                if(c.getImage2()!=null) {
                    Glide.with(getApplicationContext())
                            .load(c.getImage2())
                            .into(imageView);
                }else {
                    Toast.makeText(LaptopDescriptionActivity.this, "Color not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });





        image =c.getImage();
        Glide.with(getApplicationContext())
                .load(c.getImage())
                .into(imageView);








    }

}
