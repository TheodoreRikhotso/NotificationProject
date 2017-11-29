package com.example.admin.notificationproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PhoneDescriptionActivity extends AppCompatActivity {

    VerticalStepView verticalStepView;
    ImageView imageView;
    TextView tvRamPh, tvBattery, tvOsPhone, tvMemory, tvColorBooked;
    Button btnRequest;
    Button btnBlackPhone, btnGreyPhone, btnGoldPhone;
    private String image, name, id, typeOs;
    private DatabaseReference databaseUserItem;
    private StorageReference mStorageReference;
    private FirebaseUser user;
    private PhonePojo c;
    private ProfilePojo a;
    private int quantity;
    private PhonePojo person;
    private String seletedColor = "black";
    private Boolean check = false;
    private DatabaseReference dbRequest;
    private UserItemPojo userItemPojo;
    private boolean isDone = false;
    private String reason;
    private UserItemPojo item;

    RingProgressBar ringProgressBar1, ringProgressBar2;
    TextView tvDay, Loading;
    ImageView ivIcon;
    Button btnOk;
    private DatabaseReference databaseProfile;

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
        setContentView(R.layout.activity_phone_description);

        verticalStepView = (VerticalStepView) findViewById(R.id.vStV);


        Intent intent = getIntent();
        c = (PhonePojo) intent.getSerializableExtra("select");

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


        tvRamPh = (TextView) findViewById(R.id.tvRamPh);
        tvMemory = (TextView) findViewById(R.id.tvMemory);
        tvOsPhone = (TextView) findViewById(R.id.tvOsPhone);
        tvBattery = (TextView) findViewById(R.id.tvBattery);


        imageView = (ImageView) findViewById(R.id.imageView);
        btnRequest = (Button) findViewById(R.id.btnRequest);

        //usse item requested
        userItemPojo = new UserItemPojo();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        id = user.getUid();


        dbRequest = FirebaseDatabase.getInstance().getReference("Users/" + id + "/Requested");

        databaseProfile = FirebaseDatabase.getInstance().getReference("Profiles");


        final Query databaseUsers = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() + "/History").orderByChild("typeOs").equalTo("ios").limitToLast(1);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()) {


                            for (final DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {


                                final UserItemPojo item = catalogSnapshot.getValue(UserItemPojo.class);


                                if (item != null) {

                                    if (item.getTypeOs().toLowerCase().contains("ios")) {
                                        if (c.getOs().toLowerCase().contains("ios")) {
                                            if (item.getItemReturn() == true) {


                                                if (item.getItemReturn() == true) {


                                                    inputBox();
                                                }
                                            } else {
                                                Snackbar.make(view, "You cannot book a android phone before can return the recently booked", Snackbar.LENGTH_LONG).show();
                                            }
                                            Toast.makeText(PhoneDescriptionActivity.this, item.getTypeOs().toLowerCase(), Toast.LENGTH_SHORT).show();
                                        } else {
//                                            Snackbar.make(view, "You cannot book a android phone before can return the recently booked", Snackbar.LENGTH_LONG).show();
                                            if (item.getTypeOs().toLowerCase().contains("android")) {
                                                if (c.getOs().toLowerCase().contains("android")) {
                                                    if (item.getItemReturn() == true) {

                                                        Toast.makeText(PhoneDescriptionActivity.this, "contain ios " + item.getTypeOs(), Toast.LENGTH_SHORT).show();

                                                        if (item.getItemReturn() == true) {


                                                            inputBox();
                                                        }
                                                    }

                                                } else {
                                                    Snackbar.make(view, "You cannot book a android phone before can return the recently booked", Snackbar.LENGTH_LONG).show();
                                                }
                                            }
                                            Toast.makeText(PhoneDescriptionActivity.this, item.getTypeOs().toLowerCase(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
//                                        inputBox();
                                    }


                                } else {


                                }
                            }

//

                        } else {

                            if (!c.getOs().toLowerCase().contains("ios")) {
                                inputBox();
                                Toast.makeText(PhoneDescriptionActivity.this, c.getOs(), Toast.LENGTH_SHORT).show();
                            }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(PhoneDescriptionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                Query databaseAndroid = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() + "/History").orderByChild("typeOs").equalTo("andr").limitToLast(1);

                databaseAndroid.addValueEventListener(new ValueEventListener() {
                                                                  @Override
                                                                  public void onDataChange(final DataSnapshot dataSnapshot) {
                                                                      Toast.makeText(PhoneDescriptionActivity.this, "android", Toast.LENGTH_SHORT).show();
                                                                              if (dataSnapshot.hasChildren()) {


                                                                                  for (final DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {


                                                                                      final UserItemPojo item = catalogSnapshot.getValue(UserItemPojo.class);


                                                                                      if (item != null) {

                                                                                          if (item.getTypeOs().toLowerCase().contains("ios")) {
                                                                                              if (c.getOs().toLowerCase().contains("ios")) {
                                                                                                  if (item.getItemReturn() == true) {


                                                                                                      if (item.getItemReturn() == true) {


                                                                                                          inputBox();
                                                                                                      }
                                                                                                  } else {
                                                                                                      Snackbar.make(view, "You cannot book a android phone before can return the recently booked", Snackbar.LENGTH_LONG).show();
                                                                                                  }

                                                                                              } else {
//                                            Snackbar.make(view, "You cannot book a android phone before can return the recently booked", Snackbar.LENGTH_LONG).show();
                                                                                                  if (item.getTypeOs().toLowerCase().contains("android")) {
                                                                                                      if (c.getOs().toLowerCase().contains("android")) {
                                                                                                          if (item.getItemReturn() == true) {

                                                                                                              Toast.makeText(PhoneDescriptionActivity.this, "contain ios " + item.getTypeOs(), Toast.LENGTH_SHORT).show();

                                                                                                              if (item.getItemReturn() == true) {


                                                                                                                  inputBox();
                                                                                                              }
                                                                                                          }

                                                                                                      } else {
                                                                                                          Snackbar.make(view, "You cannot book a android phone before can return the recently booked", Snackbar.LENGTH_LONG).show();
                                                                                                      }
                                                                                                  }
                                                                                                  Toast.makeText(PhoneDescriptionActivity.this, item.getTypeOs().toLowerCase(), Toast.LENGTH_SHORT).show();
                                                                                              }
                                                                                          } else {
//                                        inputBox();
                                                                                          }


                                                                                      } else {


                                                                                      }

                                                                                  }

                                                                              } else {

                                                                                  if (!c.getOs().toLowerCase().contains("android")) {
                                                                                      inputBox();
                                                                                      Toast.makeText(PhoneDescriptionActivity.this, c.getOs(), Toast.LENGTH_SHORT).show();
                                                                                  }


                                                                              }



                                                                  }

                                                                  @Override
                                                                  public void onCancelled(DatabaseError databaseError) {
                                                                      Toast.makeText(PhoneDescriptionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                                  }
                                                              });
            }
        });


        btnBlackPhone = (Button) findViewById(R.id.btnBlackPhone);
        btnGoldPhone = (Button) findViewById(R.id.btnGoldPhone);
        btnGreyPhone = (Button) findViewById(R.id.btnGreyPhone);


        btnBlackPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btnBlackPhone.setBackground(getDrawable(R.drawable.white_selected_button));
                btnBlackPhone.setScaleX((float) 1.3);
                btnBlackPhone.setScaleY((float) 1.3);

                btnGoldPhone.setScaleX(1);
                btnGoldPhone.setScaleY(1);

                btnGreyPhone.setScaleX(1);
                btnGreyPhone.setScaleY(1);
                seletedColor = "black";


                if (c.getImage2() != null) {
                    Glide.with(getApplicationContext())
                            .load(c.getImage())
                            .into(imageView);
                } else {
                    Toast.makeText(PhoneDescriptionActivity.this, "Color not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnGoldPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btnGoldPhone.setBackground(getDrawable(R.drawable.red_selected_botton));
                btnGoldPhone.setScaleX((float) 1.3);
                btnGoldPhone.setScaleY((float) 1.3);

                btnBlackPhone.setScaleX(1);
                btnBlackPhone.setScaleY(1);

                btnGreyPhone.setScaleX(1);
                btnGreyPhone.setScaleY(1);
                seletedColor = "Gold";


                if (c.getImage1() != null) {
                    Glide.with(getApplicationContext())
                            .load(c.getImage2())
                            .into(imageView);
                } else {
                    Toast.makeText(PhoneDescriptionActivity.this, "Color not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGreyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);

                btnGreyPhone.setBackground(getDrawable(R.drawable.black_selected_button));
                btnGreyPhone.setScaleX((float) 1.3);
                btnGreyPhone.setScaleY((float) 1.3);

                btnBlackPhone.setScaleX(1);
                btnBlackPhone.setScaleY(1);

                btnGoldPhone.setScaleX(1);
                btnGoldPhone.setScaleY(1);
                seletedColor = "Grey";
                if (c.getImage() != null) {
                    Glide.with(getApplicationContext())
                            .load(c.getImage1())
                            .into(imageView);
                } else {
                    Toast.makeText(PhoneDescriptionActivity.this, "Color not Available", Toast.LENGTH_SHORT).show();
                }


            }
        });


//

        //FETCHING USING CLASS
        tvBattery.setText(c.getBattery());
        tvMemory.setText(c.getMemory());
        tvOsPhone.setText(c.getOs());
        tvRamPh.setText(c.getRam());
        name = c.getTitle();
        typeOs = c.getOs();


        image = c.getImage();
        Glide.with(getApplicationContext())
                .load(c.getImage())
                .into(imageView);


    }

    private void getDialogBook() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseUserItem = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/History");
        ///DIALOG BOX INITIALIZATION
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PhoneDescriptionActivity.this);
        mBuilder.setCancelable(false);
        View mView = getLayoutInflater().inflate(R.layout.activity_confirm_request_activty, null);

        btnOk = mView.findViewById(R.id.btnOk);
        tvDay = mView.findViewById(R.id.tvDay);
        ivIcon = mView.findViewById(R.id.ivIcon);


        final Date currentTimes = Calendar.getInstance().getTime();
        final DateFormat dateFormatx = new SimpleDateFormat("dd MMM yyyy");
        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        Calendar calc = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        //Date, department

        // CONFIRMATION TV'S
        final TextView tvDate = mView.findViewById(R.id.tvDates);

        final TextView tvColorBooked = mView.findViewById(R.id.tvColorBooked);
        tvColorBooked.setText(seletedColor);
        final TextView tvDert = mView.findViewById(R.id.tvDert);
        final TextView tvType = mView.findViewById(R.id.tvType);

        ProfilePojo a = new ProfilePojo();

        //SETS CONFIRMATION TV'S
        tvDate.setText(dateFormatx.format(currentTimes));
        tvDert.setText(a.getDepartmentName());


        ringProgressBar2 = mView.findViewById(R.id.progress_bar_2);
        Loading = mView.findViewById(R.id.Loading);

        //
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Profile");


        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {


                    ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);

                    if (person != null) {
                        tvDert.setText(person.getDepartmentName());
                        tvType.setText(LandingScreen.ACYIVITY);

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PhoneDescriptionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

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
                        if (progress == 100) {
                            isDone = true;
                        }
                        if (progress >= 100) {
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    userItemPojo.setName(name);
                                    userItemPojo.setImageUri(image);
                                    userItemPojo.setDeviceId(id);
                                    userItemPojo.setTypeDevice("Phone");
                                    userItemPojo.setItemReturn(false);
                                    userItemPojo.setBookingStatus("Booked");
                                    userItemPojo.setColor(seletedColor);
                                    userItemPojo.setTypeOs(typeOs);


                                    //returned date
                                    Date dt = new Date();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(dt);
                                    calendar.add(Calendar.DATE, 30);
                                    dt = calendar.getTime();


                                    Date currentTime = Calendar.getInstance().getTime();
                                    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                                    //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));


                                    Date currentLocalTime = cal.getTime();
                                    DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
                                    date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));


                                    String requested_Id = dbRequest.push().getKey();

                                    String history_Id = databaseUserItem.push().getKey();

                                    userItemPojo.setHistoryId(history_Id);
                                    userItemPojo.setReturnDate(dateFormat.format(dt));


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
                                    final DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Devices/Phones/details");


                                    final DatabaseReference buisnessAccRef = databaseUser.child(c.getId());
                                    buisnessAccRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot != null) {

                                                person = dataSnapshot.getValue(PhonePojo.class);
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


                                    String localTime = date.format(currentLocalTime);
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//                LocalDateTime now = LocalDateTime.now();
                                    userItemPojo.setItemDate(dateFormatx.format(currentTime));
                                    userItemPojo.setItemTime(localTime);
                                    userItemPojo.setReturnDate(dateFormatx.format(dt));
                                    userItemPojo.setReasonForBooking(reason);


                                    //String userId = databaseUserItem.push().getKey();

                                    databaseUserItem.child(history_Id).setValue(userItemPojo);

                                    Intent intents = new Intent(PhoneDescriptionActivity.this, ProfileActivity.class);
                                    startActivity(intents);
                                }
                            });
                        }


                    }
                });
            }
        }).start();


        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        //DIALOG END

    }

    private void inputBox() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt_text, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        final TextView tvReason = (TextView) promptsView.findViewById(R.id.tvReason);
        tvReason.setText("State reason for booking a phone");
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Request",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text

                                reason = userInput.getText().toString();
                                if (reason.isEmpty()) {
                                    Toast.makeText(PhoneDescriptionActivity.this, "State the reason", Toast.LENGTH_SHORT).show();
                                } else {

                                    getDialogBook();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
