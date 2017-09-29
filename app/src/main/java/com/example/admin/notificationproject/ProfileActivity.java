package com.example.admin.notificationproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.admin.notificationproject.R.drawable.circle;
import static com.example.admin.notificationproject.R.id.imageView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        image =(CircleImageView)findViewById(R.id.avatar);
         nameS =(TextView)findViewById(R.id.txtName);
        tvDepartment =(TextView)findViewById(R.id.tvDepartment);
        tvStuffNo =(TextView)findViewById(R.id.tvStuffNo);
        ivEdit = (ImageView)findViewById(R.id.ivEdit);




        mStorageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        databaseProfile = FirebaseDatabase.getInstance().getReference("Profiles");
       if(SignUpActivity.CONTEXT=="SignUpActivity") {


           nameS.setVisibility(View.VISIBLE);
           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
           ProfilePojo profile = new ProfilePojo();
           profile.setDepartmentName(department);
           mainDepart = department;
           mainImage =SignUpActivity.stuffno;

           profile.setStuffNo(SignUpActivity.stuffno);
           profile.setName("Theo");
           profile.setId(user.getUid());
           Toast.makeText(this, department+" "+SignUpActivity.stuffno, Toast.LENGTH_SHORT).show();

           databaseProfile.child(user.getUid()).setValue(profile);
       }

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDialog(R.style.DialogAnimation_Profile);
            }
        });
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("Profiles");

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    ProfilePojo person = catalogSnapshot.getValue(ProfilePojo.class);
                    mainName =person.getName();
                    mainDepart = person.getDepartmentName();
                    mainStuffNo =person.getStuffNo();
                    mainImage =person.getImage();


                    nameS.setText(mainName);
                    nameS.setVisibility(View.VISIBLE);
                    tvDepartment.setText(department);
                    tvStuffNo.setText(mainStuffNo);

                    Toast.makeText(ProfileActivity.this, person.getImage()+"", Toast.LENGTH_SHORT).show();
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
    private void buildDialog(int animationSource) {
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





        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                if (filePath != null) {
                    pd.show();

                    StorageReference childRef = mStorageReference.child("ProfileImage").child(filePath.getLastPathSegment());;

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Uri uir = taskSnapshot.getDownloadUrl();
                            profileUri = uir.toString();
                            Toast.makeText(ProfileActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(ProfileActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ProfileActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String department,name,stuffNo;
                    name  =editName.getText().toString();
                    department =editDepartment.getText().toString();
                    stuffNo = editStuffNo.getText().toString();
                    ProfilePojo profilePojo = new ProfilePojo();
                    profilePojo.setDepartmentName(department);
                    Toast.makeText(ProfileActivity.this, ""+profileUri, Toast.LENGTH_SHORT).show();
                    profilePojo.setImage(profileUri);
                    profilePojo.setName(name);
                    profilePojo.setStuffNo(stuffNo);

                    FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                    databaseProfile.child(users.getUid()).setValue(profilePojo);
                    builder.dismiss();
                    setContentView(R.layout.activity_profile);

                }
            });




        builder.getWindow().getAttributes().windowAnimations = animationSource;
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            uri = data.getData();
            ivPhoto.setImageURI(uri);

        }
    }
}
