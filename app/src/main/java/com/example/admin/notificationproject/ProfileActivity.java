package com.example.admin.notificationproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.admin.notificationproject.SignUpActivity.department;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CircleImageView image;
    private TextView nameS,tvDepartment,tvStuffNo,txtName,tvOk;
    private ImageView ivEdit;
    private EditText editName,editDepartment,editStuffNo;
    private int PICK_IMAGE_REQUEST = 111;
    CircleImageView ivPhoto;
    Uri filePath;
    private StorageReference mStorageReference;
    private Uri uri;
    ProgressDialog pd;
    String profileUri;
    private Dialog builder;
    private String mainName,mainStuffNo,mainDepart,mainImage;
    ///
    private DatabaseReference databaseProfile;

    //user Items
    List<UserItemPojo> userItemPojos;
    RecyclerView rvUserItems;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarCar) ;
        toolbar.setTitle("Profile");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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



        image =(CircleImageView)findViewById(R.id.avatar);
         nameS =(TextView)findViewById(R.id.txtName);
        tvDepartment =(TextView)findViewById(R.id.tvDepartment);
        tvStuffNo =(TextView)findViewById(R.id.tvStuffNo);
        ivEdit = (ImageView)findViewById(R.id.ivEdit);



        //user item
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("UserItems");
        DatabaseReference databaseItem = databaseItems.child(user.getUid());

        rvUserItems = (RecyclerView) findViewById(R.id.rvUserItems);
        userItemPojos = new ArrayList<>();

       final  String ids =user.getUid();

        Query query = FirebaseDatabase.getInstance().getReference().child("UserItems").orderByChild("refId").equalTo(user.getUid());
//        db.child("users").orderByChild("userBloodGroup").equalTo("B+").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                List<User> data = new ArrayList<>();
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot :
//                            dataSnapshot.getChildren()) {
//                        User element = snapshot.getValue(User.class);
//                        element.setKey(snapshot.getKey());
//                        data.add(element);
//
//                    }
//                    for (User user: data){
//                        Log.i(TAG,"user name: " +user.getName());
//                        Log.i(TAG,"user email: " +user.getEmail());
//                        Log.i(TAG,"user dob: " +user.getUserDob());
//                    }
//        databaseItems.child("refId").orderByChild("refId").equalTo(user.getUid());
//        databaseItems.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                userItemPojos.clear();
//                if(dataSnapshot!=null) {
//                    UserItemPojo item = dataSnapshot.getValue(UserItemPojo.class);
//                    if(item!=null) {
//
//
//                        userItemPojos.add(item);
//                        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                        UserItemAdapter adapterss = new UserItemAdapter(ProfileActivity.this, userItemPojos);
//
////
//                        rvUserItems.setLayoutManager(layoutManager);
//
//                        rvUserItems.setAdapter(adapterss);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                userItemPojos.clear();
//                if(dataSnapshot!=null) {
//                    UserItemPojo item = dataSnapshot.getValue(UserItemPojo.class);
//                    if(item!=null) {
//
//
//                        userItemPojos.add(item);
//                        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                        UserItemAdapter adapterss = new UserItemAdapter(ProfileActivity.this, userItemPojos);
//
////
//                        rvUserItems.setLayoutManager(layoutManager);
//
//                        rvUserItems.setAdapter(adapterss);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userItemPojos.clear();

                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    UserItemPojo item = catalogSnapshot.getValue(UserItemPojo.class);

                    int numberOfColumns = 4;
                    rvUserItems.setLayoutManager(new GridLayoutManager(ProfileActivity.this, numberOfColumns));
                    userItemPojos.add(item);
                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.d);
                    rvUserItems.addItemDecoration(itemDecoration);
                    //layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    UserItemAdapter adapterss = new UserItemAdapter(ProfileActivity.this, userItemPojos);
                    adapterss.setHasStableIds(true);
                    adapterss.notifyDataSetChanged();

                   // rvUserItems.setLayoutManager(layoutManager);

                    rvUserItems.setAdapter(adapterss);
                }
            }






            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mStorageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        databaseProfile = FirebaseDatabase.getInstance().getReference("Profiles");
       if(SignUpActivity.CONTEXT=="SignUpActivity") {


           nameS.setVisibility(View.VISIBLE);
           FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
           ProfilePojo profile = new ProfilePojo();
           profile.setDepartmentName(department);
           mainDepart = department;
           mainImage =SignUpActivity.stuffno;

           profile.setStuffNo(SignUpActivity.stuffno);
           profile.setName("Theo");
           profile.setId(users.getUid());


           databaseProfile.child(user.getUid()).setValue(profile);
       }

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDialog(R.style.DialogAnimation_Profile);
            }
        });
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Profiles");

        DatabaseReference buisnessAccRef = databaseUser.child(user.getUid());

        buisnessAccRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

        if(dataSnapshot!= null) {
            ProfilePojo person = dataSnapshot.getValue(ProfilePojo.class);
            if(person!= null) {
                mainName = person.getName();
                mainDepart = person.getDepartmentName();
                mainStuffNo = person.getStuffNo();
                mainImage = person.getImage();


                nameS.setText(mainName);
                nameS.setVisibility(View.VISIBLE);
                tvDepartment.setText(mainDepart);
                tvDepartment.setVisibility(View.VISIBLE);
                tvStuffNo.setText(mainStuffNo);


                Glide.with(ProfileActivity.this).load(person.getImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(image) {
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
                Intent intent = new Intent(this,AboutImageActivity.class);
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
        }
    }
    private void buildDialog(int animationSource) {
        final ProfilePojo profilePojo = new ProfilePojo();
        builder = new Dialog(this);

        builder.setTitle("Edit Profile");
        builder.setContentView(R.layout.edit_profile_dialog);
        //builder.setNegativeButton("OK", null);

        ivPhoto =builder.findViewById(R.id.editMainPhoto);
        editDepartment =builder.findViewById(R.id.editDepartment);
        editStuffNo = builder.findViewById(R.id.editStuffNo);
        editName = builder.findViewById(R.id.editName);
        tvOk = builder.findViewById(R.id.tvOk);
        editDepartment.setText(mainDepart);
        editName.setText(mainName);
        editStuffNo.setText(mainStuffNo);



        Glide.with(ProfileActivity.this).load(mainImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivPhoto) {
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
                    final String department,name,stuffNo;
                    name  =editName.getText().toString();
                    department =editDepartment.getText().toString();
                    stuffNo = editStuffNo.getText().toString();

                        StorageReference childRef = mStorageReference.child("ProfileImage").child(filePath.getLastPathSegment());;

                        //uploading the image
                        UploadTask uploadTask = childRef.putFile(filePath);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();
                                @SuppressWarnings("VisibleForTests")  Uri uir = taskSnapshot.getDownloadUrl();
                                profileUri = uir.toString();

                                profilePojo.setImage(uir.toString());
                               profilePojo.setDepartmentName(department);
                                profilePojo.setName(name);
                                profilePojo.setStuffNo(stuffNo);

                                FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                                databaseProfile.child(users.getUid()).setValue(profilePojo);

                                Toast.makeText(ProfileActivity.this, "Upload successful " , Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(ProfileActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                            }
                        });





                    builder.dismiss();


                }
            });




        builder.getWindow().getAttributes().windowAnimations = animationSource;
        builder.show();
    }


}
