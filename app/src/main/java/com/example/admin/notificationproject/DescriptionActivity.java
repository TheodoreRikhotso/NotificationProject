package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class DescriptionActivity extends AppCompatActivity {

    VerticalStepView verticalStepView;
    ImageView imageView;
    TextView tvDisplay, tvSize, tvCapacity, tvCamera,Color1, Color2, Color3,Color4, Color5 ;
    Button btnRequest;
    Button btn_red,btn_blue,btn_gray,btn_white,btn_black,btnEnter;
    private String image,name,id;
    private DatabaseReference databaseUserItem;
    private StorageReference mStorageReference;
    private  FirebaseUser user;

    RingProgressBar ringProgressBar1, ringProgressBar2;
    TextView tvDay, Loading;
    ImageView ivIcon;
    Button btnOk;

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
        setContentView(R.layout.activity_description);

        // INVOKES BACK ARROW
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verticalStepView = (VerticalStepView)findViewById(R.id.vStV);

        Intent intent = getIntent();
        Catalog c = (Catalog)intent.getSerializableExtra("select");

        Toolbar toolbar = (Toolbar)findViewById(R.id.tbDescrption) ;
        toolbar.setTitle(c.getAssetTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tvSize = (TextView) findViewById(R.id.tvSize);
        tvCamera = (TextView) findViewById(R.id.tvCamera);
        tvCapacity = (TextView) findViewById(R.id.tvCapacity);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);


        imageView = (ImageView)findViewById(R.id.imageView);
        btnRequest = (Button)findViewById(R.id.btnRequest);

        //usser item requested

        databaseUserItem = FirebaseDatabase.getInstance().getReference("UserItems");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        id = user.getUid();


        databaseUserItem = FirebaseDatabase.getInstance().getReference("UserItems");
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserItemPojo userItemPojo = new UserItemPojo();
                userItemPojo.setName(name);
                userItemPojo.setImageUri(image);
                userItemPojo.setRefId(id);
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
                date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

                String localTime = date.format(currentLocalTime);
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//                LocalDateTime now = LocalDateTime.now();
                userItemPojo.setItemDate(dateFormat.format(currentTime)+"  "+localTime);

                String userId = databaseUserItem.push().getKey();

                databaseUserItem.child(userId).setValue(userItemPojo);
              ///DIALOG BOX INITIALIZATION
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DescriptionActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_confirm_request_activty, null);

                btnOk = mView.findViewById(R.id.btnOk);
                tvDay = mView.findViewById(R.id.tvDay);
                ivIcon = mView.findViewById(R.id.ivIcon);
                ringProgressBar2 = mView.findViewById(R.id.progress_bar_2);
                Loading = mView.findViewById(R.id.Loading);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intents = new Intent(DescriptionActivity.this,LandingScreen.class);
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

        btn_red = (Button)findViewById(R.id.btn_red);
        btn_black = (Button)findViewById(R.id.btn_black);
        btn_gray = (Button)findViewById(R.id.btnGrey);
        btn_white = (Button)findViewById(R.id.btnWhite);
        btn_blue = (Button)findViewById(R.id.btnBlue);


        btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_red.setBackground(getDrawable(R.drawable.red_selected_botton));
                btn_red.setScaleX((float) 1.3);
                btn_red.setScaleY((float) 1.3);

                btn_white.setScaleX( 1);
                btn_white.setScaleY( 1);

                btn_blue.setScaleX(1);
                btn_blue.setScaleY(1);

                btn_gray.setScaleX(1);
                btn_gray.setScaleY(1);

                btn_black.setScaleX(1);
                btn_black.setScaleY(1);
            }
        });



        btn_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_black.setBackground(getDrawable(R.drawable.black_selected_button));
                btn_black.setScaleX((float) 1.3);
                btn_black.setScaleY((float) 1.3);

                btn_white.setScaleX( 1);
                btn_white.setScaleY( 1);

                btn_blue.setScaleX(1);
                btn_blue.setScaleY(1);

                btn_gray.setScaleX(1);
                btn_gray.setScaleY(1);

                btn_red.setScaleX(1);
                btn_red.setScaleY(1);
            }
        });

        btn_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_gray.setBackground(getDrawable(R.drawable.grey_selected_button));
                btn_gray.setScaleX((float) 1.3);
                btn_gray.setScaleY((float) 1.3);

                btn_white.setScaleX( 1);
                btn_white.setScaleY( 1);

                btn_blue.setScaleX(1);
                btn_blue.setScaleY(1);

                btn_red.setScaleX(1);
                btn_red.setScaleY(1);

                btn_black.setScaleX(1);
                btn_black.setScaleY(1);
            }
        });

        btn_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_white.setBackground(getDrawable(R.drawable.white_selected_button));
                btn_white.setScaleX((float) 1.3);
                btn_white.setScaleY((float) 1.3);

                btn_red.setScaleX( 1);
                btn_red.setScaleY( 1);

                btn_blue.setScaleX(1);
                btn_blue.setScaleY(1);

                btn_gray.setScaleX(1);
                btn_gray.setScaleY(1);

                btn_black.setScaleX(1);
                btn_black.setScaleY(1);
            }
        });

        btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_red.segetResources().getDrawable(R.drawable.red_selected_botton);
                btn_blue.setBackground(getDrawable(R.drawable.blue_selected_button));
                btn_blue.setScaleX((float) 1.3);
                btn_blue.setScaleY((float) 1.3);

                btn_white.setScaleX( 1);
                btn_white.setScaleY( 1);

                btn_red.setScaleX(1);
                btn_red.setScaleY(1);

                btn_gray.setScaleX(1);
                btn_gray.setScaleY(1);

                btn_black.setScaleX(1);
                btn_black.setScaleY(1);
            }
        });





//        Toast.makeText(this, c.getContent1(), Toast.LENGTH_SHORT).show();
        tvDisplay.setText(c.getDiplay());
        tvCapacity.setText(c.getCapacity());
        tvSize.setText(c.getSizeAndWieght());
        tvCamera.setText(c.getCamera());

        name=c.getDiplay();


//        List<String> source = new ArrayList<>();
//        source.add(c.getDiplay());
//
//        source.add(c.getDiplay());
//        source.add(c.getCapacity());
//        source.add(c.getCamera());
//        source.add(c.getSizeAndWieght());
//
//        verticalStepView.setStepsViewIndicatorComplectingPosition(source.size()-2)
//                .reverseDraw(false)
//                .setStepViewTexts(source)
//                .setLinePaddingProportion(0.85f)
//                .setStepViewUnComplectedTextColor(Color.parseColor("#808080"))
//                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#008000"))
//                .setStepViewUnComplectedTextColor(Color.parseColor("#ffff00"))
//                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this,R.drawable.unchecked))
//                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this,R.drawable.check))
//                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this,R.drawable.unchecked))
//                .setStepViewComplectedTextColor(Color.parseColor("#000000"))
//                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#228B22"));

//        Color1.setText(c.getColor1());
//        Color2.setText(c.getColor2());
//        Color3.setText(c.getColor3());
//        Color4.setText(c.getColor4());
//        Color5.setText(c.getColor5());



        image =c.catalogimageurl;
        Glide.with(getApplicationContext())
                .load(c.catalogimageurl)
                .into(imageView);











    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.notification, menu);
//        return true;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent intent = new Intent(DescriptionActivity.this,AboutImageActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_help:

                return true;
            case R.id.menu_logout:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }


