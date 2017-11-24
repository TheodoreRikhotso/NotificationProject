package com.example.admin.notificationproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class FurnitureDescriptionActivity extends AppCompatActivity {

    VerticalStepView verticalStepView;
    ImageView imageView;
    TextView tvQuantity, tvTypes, tvDeliverance, tvDuration;
    Button btnRequest;
    Button btn_red, btn_blue, btn_gray, btn_white, btn_black, btnEnter;
    private String image, name, id;
    private DatabaseReference databaseUserItem, dbRequest;
    private StorageReference mStorageReference;
    private FirebaseUser user;
    private FurniturePojo c;

    RingProgressBar ringProgressBar1, ringProgressBar2;
    TextView tvDay, Loading;
    ImageView ivIcon;
    Button btnOk;
    private DatabaseReference databaseProfile;
    ///Quantity
    private int quantity;
    private FurniturePojo person;
    private Boolean check = false;


    int progress = 0;
    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (progress < 100) {
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
        setContentView(R.layout.activity_furniture_description);
        verticalStepView = (VerticalStepView) findViewById(R.id.vStV);

        Intent intent = getIntent();
        c = (FurniturePojo) intent.getSerializableExtra("select");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbDescrption);
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


        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvTypes = (TextView) findViewById(R.id.tvTypes);
        tvDeliverance = (TextView) findViewById(R.id.tvDeliverance);
        tvDuration = (TextView) findViewById(R.id.tvDuration);


        imageView = (ImageView) findViewById(R.id.imageView);
        btnRequest = (Button) findViewById(R.id.btnRequest);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        id = user.getUid();


        databaseUserItem = FirebaseDatabase.getInstance().getReference("Users/" + id + "/History");
        dbRequest = FirebaseDatabase.getInstance().getReference("Users/" + id + "/Requested");

        Query databaseUsers = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() + "/History").orderByChild("typeDevice").equalTo("Furniture").limitToLast(1);

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final UserItemPojo item = dataSnapshot.getValue(UserItemPojo.class);

                btnRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //GOES WITH SNACKBAR

                        if (item != null) {

                            if (item.getItemReturn() == true) {
                                getBookDialog();

                            } else {
                                Snackbar.make(view, "You cannot book a Assert before can return the recently booked", Snackbar.LENGTH_LONG).show();
                            }

                        }
                        else {
                            getBookDialog();

                        }
                    }

                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        tvQuantity.setText(c.getQuantity());
        tvDeliverance.setText(c.getDeliverance());
        tvDuration.setText(c.getDuration() + " hours");
        tvTypes.setText("" + c.getType());

        name = c.getTitle();


        image = c.getImage();
        Glide.with(getApplicationContext())
                .load(c.getImage())
                .into(imageView);


    }

    private void getBookDialog() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ///DIALOG BOX INITIALIZATION
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FurnitureDescriptionActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_confirm_request_activty, null);

        btnOk = mView.findViewById(R.id.btnOk);
        tvDay = mView.findViewById(R.id.tvDay);
        ivIcon = mView.findViewById(R.id.ivIcon);

        //Date, department

        Date currentTimes = Calendar.getInstance().getTime();
        final DateFormat dateFormats = new SimpleDateFormat("dd MMM yyyy");
        TextView tvDate = mView.findViewById(R.id.tvDates);
        final TextView tvDepartment = mView.findViewById(R.id.tvDert);
        final TextView tvType = mView.findViewById(R.id.tvType);
        tvDate.setText(dateFormats.format(currentTimes));


        ringProgressBar2 = mView.findViewById(R.id.progress_bar_2);
        Loading = mView.findViewById(R.id.Loading);


        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/Profile");


        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);
                    if (person != null) {
                        tvDepartment.setText(person.getDepartmentName());
                        tvType.setText(c.getTitle());


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FurnitureDescriptionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserItemPojo userItemPojo = new UserItemPojo();
                userItemPojo.setName(name);
                userItemPojo.setImageUri(image);
                userItemPojo.setDeviceId(c.getId());
                userItemPojo.setTypeDevice("Furniture");
                userItemPojo.setItemReturn(false);
                userItemPojo.setBookingStatus("booked");


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

                //returned date
                Date dt = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dt);
                calendar.add(Calendar.DATE, 30);
                dt = calendar.getTime();


                String requested_Id = dbRequest.push().getKey();

                String history_Id = databaseUserItem.push().getKey();
                userItemPojo.setHistoryId(history_Id);

                userItemPojo.setReturnDate(dateFormat.format(dt));

                databaseUserItem.child(history_Id).setValue(userItemPojo);
                Requested requested = new Requested();
                requested.setDevice_id(c.getId());
                System.out.println("History " + history_Id);


                dbRequest.child(requested_Id).setValue(requested);
                final DatabaseReference dbBookings = FirebaseDatabase.getInstance().getReference("Devices/Furniture/Bookings/Booked_By");


                dbBookings.child("user_id").setValue(id);

                final DatabaseReference dbBooking_queue = FirebaseDatabase.getInstance().getReference("Devices/Furniture/Bookings/Booking_Queue");

                String book_queue_id = dbBooking_queue.push().getKey();

                dbBooking_queue.child(book_queue_id).child("history_Id").setValue(history_Id);


                //remove quantity
                final DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Devices/Furniture/details");


                final DatabaseReference buisnessAccRef = databaseUser.child(c.getId());
                buisnessAccRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {

                            person = dataSnapshot.getValue(FurniturePojo.class);
                            if (check == false) {
                                check = true;
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


                Intent intents = new Intent(FurnitureDescriptionActivity.this, ProfileActivity.class);
                startActivity(intents);

            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 100; i++) {
                    try {
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
        // DIALOG END
    }
}
