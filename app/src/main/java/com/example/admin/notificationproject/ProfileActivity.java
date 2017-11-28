package com.example.admin.notificationproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CircleImageView image;


    private TextView nameS, tvDepartment, txtName, tvOk, empty;
    //    private ImageView ivEdit;
    private EditText editName, editDepartment, editStuffNo;
    private int PICK_IMAGE_REQUEST = 111;

    CircleImageView ivPhoto;
    FloatingActionButton floatingEdit;
    Uri filePath;
    private StorageReference mStorageReference;
    private Uri uri, uriSecond;
    ProgressDialog pd;
    String profileUri;
    private Dialog builder;
    private String mainName, mainStuffNo, mainDepart, mainImage, secondImage;
    String isImage = "1";
    public static int dayDiffer;


    ///
    private DatabaseReference databaseProfile;

    //user Items
    List<UserItemPojo> userItemPojos;
    RecyclerView rvUserItems;
    LinearLayoutManager layoutManager;
    private VerticalStepView verticalStepView;
    private final String[] views = {"View 1", "View 2", "View 3", "View 4", "View 5", "View 6",
            "View 7", "View 8", "View 9", "View 10", "View 11", "View 12"};
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCar);
        toolbar.setTitle("Profile");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Intent intents = new Intent(ProfileActivity.this, LandingScreen.class);
                startActivity(intents);
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);

        verticalStepView = (VerticalStepView) findViewById(R.id.vStV);

        image = (CircleImageView) findViewById(R.id.avatar);
        nameS = (TextView) findViewById(R.id.txtName);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);

//        ivEdit = (ImageView)findViewById(R.id.ivEdit);
        empty = (TextView) findViewById(R.id.tvEmpty);
        floatingEdit = (FloatingActionButton) findViewById(R.id.floatingEdit);


        //user item
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("UserItems");
//
//        DatabaseReference databaseItem = databaseItems.child(user.getUid());

        rvUserItems = (RecyclerView) findViewById(R.id.rvUserItems);
        userItemPojos = new ArrayList<>();

        final String ids = user.getUid();
        empty.setVisibility(View.VISIBLE);

        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("Users/" + ids + "/History");
        databaseProfile = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() + "/Profile");

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userItemPojos.clear();


                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    UserItemPojo item = catalogSnapshot.getValue(UserItemPojo.class);

                    empty.setVisibility(View.GONE);
                    date = item.getItemDate();
                    int numberOfColumns = 1;

                    rvUserItems.setLayoutManager(new GridLayoutManager(ProfileActivity.this, numberOfColumns));
                    userItemPojos.add(item);
                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.d);
                    rvUserItems.addItemDecoration(itemDecoration);

                    //layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                }
                UserItemAdapter adapterss = new UserItemAdapter(ProfileActivity.this, userItemPojos);
                Collections.reverse(userItemPojos);
//                        Collections.sort(userItemPojos, new Comparator<UserItemPojo>() {
//                            public int compare(UserItemPojo o1, UserItemPojo o2) {
//                                return o2.getItemDate().compareTo(o1.getItemDate());
//                            }
//                        });
                adapterss.setHasStableIds(true);
                adapterss.notifyDataSetChanged();

                // rvUserItems.setLayoutManager(layoutManager);

                rvUserItems.setAdapter(adapterss);
                List<String> source = new ArrayList<>();
                source.add("Booked");

                source.add("Waiting for approval");
                source.add("Approved");
                source.add("Pick Up");
                DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                Date currentTime = Calendar.getInstance().getTime();
                String changeDate = dateFormat.format(currentTime);







            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mStorageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


//            ivEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    buildDialog(R.style.DialogAnimation_Profile);
//                }
//            });

        floatingEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDialog(R.style.DialogAnimation_Profile);
            }
        });

        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() + "/Profile");
        // DatabaseReference buisnessAccRef = databaseUser.child(user.getUid());


        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);
                    if (person != null) {
                        mainName = person.getName();
                        mainDepart = person.getDepartmentName();

                        mainImage = person.getImage();


                        nameS.setText(mainName);
                        nameS.setVisibility(View.VISIBLE);
                        tvDepartment.setText(mainDepart);
                        tvDepartment.setVisibility(View.VISIBLE);


                        Glide.with(ProfileActivity.this).load(person.getImage()).asBitmap().centerCrop().placeholder(R.drawable.profile_icon_).into(new BitmapImageViewTarget(image) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(ProfileActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                image.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent intent = new Intent(this, AboutImageActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();


            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                ivPhoto.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ivPhoto.setImageURI(filePath);
        }

    }

    private void buildDialog(int animationSource) {
        final ProfilePojo profilePojo = new ProfilePojo();
        builder = new Dialog(this);

        builder.setTitle("Edit Profile");
        builder.setContentView(R.layout.edit_profile_dialog);
        //builder.setNegativeButton("OK", null);

        ivPhoto = builder.findViewById(R.id.editMainPhoto);
        editDepartment = builder.findViewById(R.id.editDepartment);

        editName = builder.findViewById(R.id.editName);

        tvOk = builder.findViewById(R.id.tvOk);

        editDepartment.setHint("Enter Position e.g CEO");


        if (mainDepart == null) {
            editDepartment.setHint("Enter Position e.g CEO");
        } else {
            editDepartment.setText(mainDepart);
        }


        if (mainDepart == null) {
            editName.setHint("Enter Name");
        } else {
            editName.setText(mainName);
        }


        Glide.with(ProfileActivity.this).load(mainImage).asBitmap().centerCrop().placeholder(R.drawable.profile_icon_).into(new BitmapImageViewTarget(ivPhoto) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(ProfileActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImage = "2";
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();
                final String department, name, stuffNo;
                name = editName.getText().toString();
                department = editDepartment.getText().toString();
//                    stuffNo = editStuffNo.getText().toString();


                if (isImage == "2") {
                    StorageReference childRef = mStorageReference.child("ProfileImage").child(filePath.getLastPathSegment());

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            @SuppressWarnings("VisibleForTests") Uri uir = taskSnapshot.getDownloadUrl();
                            profileUri = uir.toString();
                            ProfilePojo profilePojo = new ProfilePojo();

                            profilePojo.setImage(uir.toString());
                            profilePojo.setDepartmentName(department);
                            profilePojo.setName(name);
//            profilePojo.setStuffNo(stuffNo);

                            FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                            databaseProfile.setValue(profilePojo);

                            Toast.makeText(ProfileActivity.this, "Upload successful ", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(ProfileActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    pd.dismiss();
                    ProfilePojo profilePojo = new ProfilePojo();

                    profilePojo.setImage(mainImage);
                    profilePojo.setDepartmentName(department);
                    profilePojo.setName(name);
//    profilePojo.setStuffNo(stuffNo);
                    profilePojo.setSecondImage(secondImage);

                    FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                    databaseProfile.setValue(profilePojo);
                }


                builder.dismiss();


            }
        });


        builder.getWindow().getAttributes().windowAnimations = animationSource;
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(ProfileActivity.this, LandingScreen.class);
            finish();
            startActivity(intent);

        }
        return true;
    }

}
