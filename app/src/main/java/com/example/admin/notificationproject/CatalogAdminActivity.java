package com.example.admin.notificationproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Admin on 9/7/2017.
 */

public class CatalogAdminActivity extends AppCompatActivity {


    //FIREBASE CONNECTION
    String TAG = "URI";
    EditText etSerialNo, etTotalQty, etDuration, etOs, etRam, etBattery, etMemory, etTitle;
    Button btnAdd;
    ImageButton IBAdmin, imageView, colorImage, colorImage2;
    StorageReference filePath, filePath1, filePath2;
    Spinner spColor1, spColor2, spColor3, spColor4, spColor5;
    private String color1, color2, color3, color4, color5, edColor5;
    private ProgressDialog mDialog;

    //IMAGE
    Uri uri, uri1, uri2;
    private static final int GALLERY_INTENT = 1;
    private static final int GALLERY_INTENT01 = 2;
    private static final int GALLERY_INTENT02 = 3;
    public static Catalog NOTIFY = null;
    public static String TYPE = null;
    //FIREBASE CONNECTION
    private DatabaseReference databaseLaptops, databaseNot;
    private StorageReference mStorageReference;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_admin);

        if (MainActivity.CATA.equals("laptop")) {

            databaseLaptops = FirebaseDatabase.getInstance().getReference("Devices/Laptops/details");
            mStorageReference = FirebaseStorage.getInstance().getReference();
        }
        if (MainActivity.CATA.equals("phone")) {
            databaseLaptops = FirebaseDatabase.getInstance().getReference("Devices/Phones/details");
            mStorageReference = FirebaseStorage.getInstance().getReference();
        }
        if (MainActivity.CATA.equals("car")) {
            databaseLaptops = FirebaseDatabase.getInstance().getReference("Devices/Cars/details");
            mStorageReference = FirebaseStorage.getInstance().getReference();
        }
        if (MainActivity.CATA.equals("furniture")) {
            databaseLaptops = FirebaseDatabase.getInstance().getReference("Devices/Furniture/details");
            mStorageReference = FirebaseStorage.getInstance().getReference();
        }
        databaseNot = FirebaseDatabase.getInstance().getReference("Notificationd");


        //ITEM ADD INSTANCES
        IBAdmin = (ImageButton) findViewById(R.id.IBAdmin);
        colorImage = (ImageButton) findViewById(R.id.IBAdmin1);
        colorImage2 = (ImageButton) findViewById(R.id.IBAdmin2);


        btnAdd = (Button) findViewById(R.id.btnAdd);

//        //DESCRIPTION ADD INSTANCES
        imageView = (ImageButton) findViewById(R.id.imageView);

//
        etSerialNo = (EditText) findViewById(R.id.etSerialNo);
        etBattery = (EditText) findViewById(R.id.etBattery);
        etDuration = (EditText) findViewById(R.id.etDuration);
        etTotalQty = (EditText) findViewById(R.id.etToatalQuantity);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etMemory = (EditText) findViewById(R.id.etMemory);
        etRam = (EditText) findViewById(R.id.etRam);
        etOs = (EditText) findViewById(R.id.etOs);


        if (MainActivity.CATA.equals("phone")) {

            etMemory.setHint("Memory");
            etBattery.setHint("Battery");
            etRam.setHint("Ram");
            etOs.setHint("Operating System ");


        }
//            if(MainActivity.CATA.equals("car")) {
//                etProccessor.setHint("fuel");
//                etSize.setHint("seater");
//                etMemory.setHint("door");
//                etCamera.setHint("mileage");
//                etBattery.setHint("Engine");
//                etOs.setHint("Drive type");
//                etDipslay.setHint("gear");
//                etCapacity.setHint("Speed");
//
//
//            }Speed
        if (MainActivity.CATA.equals("laptop")) {

            etMemory.setHint("Graphics");
            etBattery.setHint("Storage");
            etRam.setHint("Ram");
            etOs.setHint("Operating System ");


        }
        if (MainActivity.CATA.equals("furniture")) {

            etMemory.setHint("Quantity");
            etBattery.setHint("Type");
            etRam.setHint("Deliverance");
            etOs.setVisibility(View.GONE);


        }


        mDialog = new ProgressDialog(this);

//
//        imageView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                //IMAGE
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALLERY_INTENT);
//            }
//        });

        IBAdmin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //IMAGE
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        colorImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //IMAGE
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT01);
            }
        });

        colorImage2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //IMAGE
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT02);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddAsset();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data.getData();
            IBAdmin.setImageURI(uri);

        }
        if (requestCode == GALLERY_INTENT01 && resultCode == RESULT_OK) {
            uri1 = data.getData();
            colorImage.setImageURI(uri1);

        }
        if (requestCode == GALLERY_INTENT02 && resultCode == RESULT_OK) {
            uri2 = data.getData();
            colorImage2.setImageURI(uri2);

        }
    }


    private void AddAsset() {


//        etMemory.setHint("Memory");
//        etBattery.setHint("Battery");
//        etRam.setHint("Ram");
//        etOs.setHint("Operating System ");
        final String title = etTitle.getText().toString().trim();
        final String serialNo = etSerialNo.getText().toString().trim();
        final String duration = etDuration.getText().toString().trim();
        final String total = etTotalQty.getText().toString().trim();
        final String memory = etMemory.getText().toString().trim();
        final String battery = etBattery.getText().toString().trim();
        final String os = etOs.getText().toString().trim();
        final String ram = etRam.getText().toString().trim();


        mDialog.setMessage("please wait ...");
        mDialog.show();

        /**
         * ADDS ENTRIES INTO CORRECT LISTS
         */

        // Log.i("T",uri.toString());
        if (MainActivity.CATA.equals("laptop")) {
            Toast.makeText(this, "Laptop ", Toast.LENGTH_SHORT).show();

                filePath = mStorageReference.child("LaptopsImages").child(uri.getLastPathSegment());
            if(uri1!= null) {
                filePath1 = mStorageReference.child("LaptopsImages").child(uri1.getLastPathSegment());
            }
            if(uri2!= null) {
                filePath2 = mStorageReference.child("LaptopsImages").child(uri2.getLastPathSegment());
            }

        }
        if (MainActivity.CATA.equals("phone")) {
            filePath = mStorageReference.child("PhonesImages").child(uri.getLastPathSegment());
            if (uri1 != null) {
                filePath1 = mStorageReference.child("PhonesImages").child(uri1.getLastPathSegment());
            }
            if (uri1 != null) {
                filePath2 = mStorageReference.child("PhonesImages").child(uri2.getLastPathSegment());
            }
        }
        if (MainActivity.CATA.equals("car")) {
            filePath = mStorageReference.child("CarsImages").child(uri.getLastPathSegment());
        }
        if (MainActivity.CATA.equals("furniture")) {



                filePath = mStorageReference.child("FurnitureImages").child(uri.getLastPathSegment());


        }
        final String ids = databaseNot.push().getKey();
        final String id = databaseLaptops.push().getKey();
        if (MainActivity.CATA.equals("furniture")) {

if(filePath!=null) {
    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            @SuppressWarnings("VisibleForTests") final Uri imageUri1 = taskSnapshot.getDownloadUrl();

            System.out.println("FURNITURE IMAGE URI: " + imageUri1);


            etMemory.setHint("Quantity");
            etBattery.setHint("Type");
            etRam.setHint("Deliverance");
            etOs.setVisibility(View.GONE);


            //CREATES A NEW UNIQUE ID IN DATABASE

            Catalog catalog = new Catalog(id, imageUri1.toString());

            catalog.setAssetTitle(title);
            FurniturePojo lap = new FurniturePojo();
            //lap.set;

            lap.setDeliverance(ram);
            lap.setType(battery);


            lap.setTotalQuantity(total);
            lap.setQuantity(memory);

            lap.setId(id);
            lap.setDuration(duration);
            lap.setSerialNo(serialNo);
            lap.setTitle(title);


            lap.setImage(imageUri1.toString());

            catalog.setCatalogimageurl(imageUri1.toString());


            ///notifacaion
            NOTIFY = catalog;
            TYPE = MainActivity.CATA;

            Date currentTime = Calendar.getInstance().getTime();
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());


            Time now = new Time();
            now.setToNow();
            NotificationPojo notificationPojo = new NotificationPojo();
            notificationPojo.setMessage(catalog.assetTitle);
            notificationPojo.setId(id);
            notificationPojo.setImageUrl(catalog.getCatalogimageurl());
            notificationPojo.setTitle(catalog.getAssetTitle());
            notificationPojo.setMessage(catalog.getAssetTitle());
            notificationPojo.setDate(dateFormat.format(currentTime));

            databaseNot.child(ids).setValue(notificationPojo);


            sendNotification(notificationPojo, MainActivity.CATA);
//                    description, content1, content2, content3, content4, content5, content6, color1, color2, color3, color4, color5,
            databaseLaptops.child(id).setValue(lap);
            mDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Done Uploading ...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
            startActivity(intent);


        }
    });
    filePath.putFile(uri).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}else {
    Toast.makeText(this, filePath.toString(), Toast.LENGTH_SHORT).show();
}

//

        }

        if (MainActivity.CATA.equals("laptop")) {

System.out.println(" file path "+filePath+" 1 "+filePath1+" 2 "+filePath2);
if(uri !=null) {
    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            @SuppressWarnings("VisibleForTests") final Uri imageUri1 = taskSnapshot.getDownloadUrl();

            System.out.println("LAPTOP IMAGE URI: " + imageUri1);

            if (filePath1 != null) {
                filePath1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests") final Uri imageUri2 = taskSnapshot.getDownloadUrl();

                        System.out.println("LAPTOP IMAGE URI: " + imageUri2);
                        if (filePath1 != null) {
                            filePath2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    @SuppressWarnings("VisibleForTests") Uri imageUri3 = taskSnapshot.getDownloadUrl();

                                    System.out.println("LAPTOP IMAGE URI: " + imageUri3);


                                    //CREATES A NEW UNIQUE ID IN DATABASE

                                    Catalog catalog = new Catalog(id, imageUri3.toString());

                                    catalog.setAssetTitle(title);
                                    Laptop lap = new Laptop();
                                    //lap.set;

                                    lap.setRam(ram);
                                    lap.setStorage(battery);

                                    lap.setOs(os);
                                    lap.setTotalQuantity(total);
                                    lap.setGraphics(memory);

                                    lap.setId(id);
                                    lap.setDuration(duration);
                                    lap.setSerialNo(serialNo);
                                    lap.setTitle(title);
                                    lap.setImage(imageUri1.toString());
                                    lap.setImage1(imageUri2.toString());
                                    lap.setImage2(imageUri3.toString());

                                    catalog.setCatalogimageurl(imageUri3.toString());


                                    ///notifacaion
                                    NOTIFY = catalog;
                                    TYPE = MainActivity.CATA;
                                    String idi = databaseNot.push().getKey();
                                    Date currentTime = Calendar.getInstance().getTime();
                                    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());


                                    Time now = new Time();
                                    now.setToNow();
                                    NotificationPojo notificationPojo = new NotificationPojo();
                                    notificationPojo.setMessage(catalog.assetTitle);
                                    notificationPojo.setId(idi);
                                    notificationPojo.setImageUrl(catalog.getCatalogimageurl());
                                    notificationPojo.setTitle(catalog.getAssetTitle());
                                    notificationPojo.setMessage(catalog.getAssetTitle());
                                    notificationPojo.setDate(dateFormat.format(currentTime));

                                    databaseNot.child(idi).setValue(notificationPojo);


                                    sendNotification(notificationPojo, MainActivity.CATA);
//                    description, content1, content2, content3, content4, content5, content6, color1, color2, color3, color4, color5,
                                    databaseLaptops.child(id).setValue(lap);
                                    mDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Done Uploading ...", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
                                    startActivity(intent);

                                }
                            });
                        }
                        Catalog catalog = new Catalog(id, imageUri1.toString());

                        catalog.setAssetTitle(title);
                        Laptop lap = new Laptop();
                        //lap.set;

                        lap.setRam(ram);
                        lap.setStorage(battery);

                        lap.setOs(os);
                        lap.setTotalQuantity(total);
                        lap.setGraphics(memory);

                        lap.setId(id);
                        lap.setDuration(duration);
                        lap.setSerialNo(serialNo);
                        lap.setTitle(title);
                        lap.setImage(imageUri1.toString());
                        lap.setImage1(imageUri2.toString());


                        catalog.setCatalogimageurl(imageUri1.toString());


                        ///notifacaion
                        NOTIFY = catalog;
                        TYPE = MainActivity.CATA;
                        String idi = databaseNot.push().getKey();
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());


                        Time now = new Time();
                        now.setToNow();
                        NotificationPojo notificationPojo = new NotificationPojo();
                        notificationPojo.setMessage(catalog.assetTitle);
                        notificationPojo.setId(idi);
                        notificationPojo.setImageUrl(catalog.getCatalogimageurl());
                        notificationPojo.setTitle(catalog.getAssetTitle());
                        notificationPojo.setMessage(catalog.getAssetTitle());
                        notificationPojo.setDate(dateFormat.format(currentTime));

                        databaseNot.child(idi).setValue(notificationPojo);

                    }
                });
            }
            Catalog catalog = new Catalog(id, imageUri1.toString());

            catalog.setAssetTitle(title);
            Laptop lap = new Laptop();
            //lap.set;

            lap.setRam(ram);
            lap.setStorage(battery);

            lap.setOs(os);
            lap.setTotalQuantity(total);
            lap.setGraphics(memory);

            lap.setId(id);
            lap.setDuration(duration);
            lap.setSerialNo(serialNo);
            lap.setTitle(title);
            lap.setImage(imageUri1.toString());


            catalog.setCatalogimageurl(imageUri1.toString());


            ///notifacaion
            NOTIFY = catalog;
            TYPE = MainActivity.CATA;
            String idi = databaseNot.push().getKey();
            Date currentTime = Calendar.getInstance().getTime();
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());


            Time now = new Time();
            now.setToNow();
            NotificationPojo notificationPojo = new NotificationPojo();
            notificationPojo.setMessage(catalog.assetTitle);
            notificationPojo.setId(idi);
            notificationPojo.setImageUrl(catalog.getCatalogimageurl());
            notificationPojo.setTitle(catalog.getAssetTitle());
            notificationPojo.setMessage(catalog.getAssetTitle());
            notificationPojo.setDate(dateFormat.format(currentTime));

            databaseNot.child(idi).setValue(notificationPojo);


        }
    });

    filePath.putFile(uri).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}


        }

        if (MainActivity.CATA.equals("phone")) {
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") final Uri imageUri1 = taskSnapshot.getDownloadUrl();

                    System.out.println("Phone IMAGE URI: " + imageUri1);

                    filePath1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") final Uri imageUri2 = taskSnapshot.getDownloadUrl();

                            System.out.println("Phone IMAGE URI: " + imageUri2);

                            filePath2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    @SuppressWarnings("VisibleForTests") Uri imageUri3 = taskSnapshot.getDownloadUrl();

                                    System.out.println("Phone IMAGE URI: " + imageUri3);


                                    //CREATES A NEW UNIQUE ID IN DATABASE

                                    Catalog catalog = new Catalog(id, imageUri3.toString());


                                    PhonePojo lap = new PhonePojo();

                                    lap.setBattery(battery);
                                    lap.setMemory(memory);
                                    lap.setRam(ram);
                                    lap.setOs(os);


                                    lap.setTotalQuantity(total);
                                    lap.setId(id);
                                    lap.setDuration(duration);
                                    lap.setSerialNo(serialNo);
                                    lap.setTitle(title);
                                    lap.setImage(imageUri1.toString());
                                    lap.setImage1(imageUri2.toString());
                                    lap.setImage2(imageUri3.toString());

                                    ///notifacaion
                                    NOTIFY = catalog;
                                    TYPE = MainActivity.CATA;
                                    String idi = databaseNot.push().getKey();
                                    Date currentTime = Calendar.getInstance().getTime();
                                    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());


                                    Time now = new Time();
                                    now.setToNow();
                                    NotificationPojo notificationPojo = new NotificationPojo();
                                    notificationPojo.setMessage(catalog.assetTitle);
                                    notificationPojo.setId(idi);
                                    notificationPojo.setImageUrl(catalog.getCatalogimageurl());
                                    notificationPojo.setTitle(catalog.getAssetTitle());
                                    notificationPojo.setMessage(catalog.getAssetTitle());
                                    notificationPojo.setDate(dateFormat.format(currentTime));

                                    databaseNot.child(idi).setValue(notificationPojo);


                                    sendNotification(notificationPojo, MainActivity.CATA);
//                    description, content1, content2, content3, content4, content5, content6, color1, color2, color3, color4, color5,
                                    databaseLaptops.child(id).setValue(lap);
                                    mDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Done Uploading ...", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    });


                }
            });
        }

    }

//

    private void sendNotification(NotificationPojo notificationPojo, String title) {

        InputStream stream = null;
        Intent intent = new Intent(this, NotificationActivity.class);
        Intent intentPP = new Intent(this, CatalogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntentS = PendingIntent.getActivity(this, 0, intentPP, PendingIntent.FLAG_ONE_SHOT);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("New " + title + " just added");
        notificationBuilder.setContentText(notificationPojo.getMessage() + " Just add");
        notificationBuilder.setSound(noti);
        notificationBuilder.setLights(Color.YELLOW, 500, 500);
        notificationBuilder.setVibrate(new long[]{100, 250, 100, 250, 100, 250});
        notificationBuilder.setPriority(0x00000001);

        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);

        Bitmap theBitmap = null;

        notificationBuilder.addAction(R.drawable.ic_remove_red_eye_black_24dp, "View", pendingIntent);
        notificationBuilder.addAction(R.drawable.ic_visibility_off_black_24dp, "Ignore", pendingIntentS);

        String picture = notificationPojo.getImageUrl();


//        Toast.makeText(this, bitmap.toString(), Toast.LENGTH_SHORT).show();

        notificationBuilder.setSmallIcon(R.drawable.ic_add_alert_black_24dp);

        Picasso.with(getApplicationContext()).load(picture).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.i(TAG, "The image was obtained correctly");
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e(TAG, "The image was not obtained");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d(TAG, "Getting ready to get the image");
            }
        });


//        String picture = "http://i.stack.imgur.com/CE5lz.png";
//        System.out.println("My URL"+picture);
////        Bitmap bmp = Picasso.with(getApplicationContext()).load(picture).get();
//
//        notificationBuilder.setLargeIcon(getBmapFromURL(notificationPojo.getImageUrl()));
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                R.drawable.mlab));
        Log.d(TAG, notificationPojo.getImageUrl());
        notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());


    }

    public Bitmap getBmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage() + "*", Toast.LENGTH_SHORT).show();
            // Log.d(TAG,e.getMessage());
            return null;
        }
    }


    public Bitmap getBitmapFromURL(String strURL) {
//        try {
//            URL url = new URL(strURL);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }

        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(strURL).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}


