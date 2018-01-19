package com.example.admin.notificationproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Admin on 08-Dec-17.
 */

public class AdminBookingsAdapter extends RecyclerView.Adapter<AdminBookingsAdapter.MyViewHolder> {
        private Activity context;
        private List<UserItemPojo> userItemPojos;
        private Activity applicationContext;
        private boolean check =false;
        private String userId;
        private int num;
    private DatabaseReference databaseUserItem;
    private DatabaseReference dataType;
    private  Spinner spStatus;
    private   String[] returned = { "Asset Returned","False","True" };

    public AdminBookingsAdapter(Activity context, List<UserItemPojo> userItemPojos,String userId) {
            this.context = context;
            this.userItemPojos = userItemPojos;
            this.userId = userId;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booked__item,null);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final UserItemPojo userItemPojo = userItemPojos.get(position);
            holder.tvItemDate.setText(userItemPojo.getItemDate()+"  "+userItemPojo.getItemTime());
            //holder.tvItemDescr.setText(userItemPojo.getName());
            holder.tvItemDescr.setText(userItemPojo.getName());

            holder.llUserItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DatabaseReference databaseReference;

                    if(userItemPojo.getBookingStatus() !=("Pick Up")) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                        // Setting Dialog Title
                        alertDialog.setTitle("Confirm Delete...");

                        // Setting Dialog Message
                        alertDialog.setMessage("Are you sure you want delete this " + userItemPojo.getTypeDevice() + " ?");

                        // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.ic_delete_forever_black_24dp);

                        // Setting Positive "Yes" Button
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Write your code here to invoke YES event
                                Toast.makeText(context, "You clicked on YES", Toast.LENGTH_SHORT).show();



                                DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/History/" + userItemPojo.getHistoryId());

                                databaseItems.removeValue();
                            }
                        });

                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event

                                dialog.cancel();
                            }
                        });

                        alertDialog.show();


                    }
                    return false;
                }

            });
            holder.llUserItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = context.getIntent();
                    ProfilePojo  profilePojo = (ProfilePojo) intent.getSerializableExtra("select");
                        final BottomSheetDialog dialog = new BottomSheetDialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                        dialog.setContentView(R.layout.booked_dialog);
                        dialog.setTitle("Title");

                        final String[] status = { "Status", "Waiting for approval", "Approved", "Pick Up",  };


                        TextView title = (TextView) dialog.findViewById(R.id.tvPopTitleBooked);
                        TextView date = (TextView) dialog.findViewById(R.id.ivPopDateBooked);
                        TextView returnDate = (TextView) dialog.findViewById(R.id.ivPopReturnDateBooked);
                        TextView color = (TextView) dialog.findViewById(R.id.ivPopColorBooked);
                        ImageView image = (ImageView) dialog.findViewById(R.id.ivPopImageBooked);
                    databaseUserItem = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/History/"+userItemPojo.getHistoryId());

                     spStatus = (Spinner)  dialog.findViewById(R.id.spStatusBooked);
                    DatabaseReference databaseReturn = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/History/" + userItemPojo.getHistoryId());
                    databaseReturn.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if(dataSnapshot!=null)
                            {


                                try {
                                    UserItemPojo userItemPojo1 =dataSnapshot.getValue(UserItemPojo.class);
                                    if(userItemPojo1.getItemReturn() ==true)
                                {
                                    spStatus.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            spStatus.setSelection(2);
                                        }
                                    });
                                }
                                }
                                catch(DatabaseException e){
                                    //Log the exception and the key
                                    dataSnapshot.getKey();
                                }

//



                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            userItemPojo.setBookingStatus(status[i]);
                            databaseUserItem.setValue(userItemPojo);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,status);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    spStatus.setAdapter(aa);
                    if(userItemPojo.getTypeDevice()=="Phone") {
                        dataType = FirebaseDatabase.getInstance().getReference("Devices/Phones/" + userItemPojo.getDeviceId());
                    }
                    if(userItemPojo.getTypeDevice()=="Furniture") {
                        dataType = FirebaseDatabase.getInstance().getReference("Devices/Furniture/" + userItemPojo.getDeviceId());
                    }
                    if(userItemPojo.getTypeDevice()=="Laptop") {
                        dataType = FirebaseDatabase.getInstance().getReference("Devices/Laptop/" + userItemPojo.getDeviceId());
                    }


                    Spinner spReturn = (Spinner)  dialog.findViewById(R.id.spReturnedBooked);
                    spReturn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Boolean state = false;
                            if (returned[i]=="True") {
                                state=true;
                                if(userItemPojo.getTypeDevice()=="Laptop") {
                                    Laptop phonePojo = new Laptop();

                                  dataType.addChildEventListener(new ChildEventListener() {
                                      @Override
                                      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                          if (dataSnapshot != null) {

                                              Laptop phonePojo = dataSnapshot.getValue(Laptop.class);
                                              if(phonePojo!=null)
                                              {
                                                 String qty= phonePojo.getTotalQuantity();
                                                  num = Integer.parseInt(qty);


                                              }
                                          }
                                      }

                                      @Override
                                      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                      }

                                      @Override
                                      public void onChildRemoved(DataSnapshot dataSnapshot) {

                                      }

                                      @Override
                                      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                      }

                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {

                                      }
                                  });
                                    num =num+1;

                                    phonePojo.setTotalQuantity(Integer.toString(num));
                                    dataType.setValue(phonePojo);
                                }

                                if(userItemPojo.getTypeDevice()=="Furniture") {

                                    FurniturePojo phonePojo = new FurniturePojo();

                                    dataType.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            if (dataSnapshot != null) {

                                                FurniturePojo phonePojo = dataSnapshot.getValue(FurniturePojo.class);
                                                if(phonePojo!=null)
                                                {
                                                    String qty= phonePojo.getTotalQuantity();
                                                    num = Integer.parseInt(qty);


                                                }
                                            }
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    num =num+1;

                                    phonePojo.setTotalQuantity(Integer.toString(num));
                                    dataType.setValue(phonePojo);


                                }

                                if(userItemPojo.getTypeDevice()=="Phone") {

                                    FurniturePojo phonePojo = new FurniturePojo();

                                    dataType.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            if (dataSnapshot != null) {

                                                FurniturePojo phonePojo = dataSnapshot.getValue(FurniturePojo.class);
                                                if(phonePojo!=null)
                                                {
                                                    String qty= phonePojo.getTotalQuantity();
                                                    num = Integer.parseInt(qty);


                                                }
                                            }
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    num =num+1;

                                    phonePojo.setTotalQuantity(Integer.toString(num));
                                    dataType.setValue(phonePojo);

                                }
                            }else
                            if (returned[i]=="False") {
                                state=false;
                            }else {
                                state=false;
                            }
                            userItemPojo.setItemReturn(state);
                            databaseUserItem.setValue(userItemPojo);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    ArrayAdapter aRetrun = new ArrayAdapter(context,android.R.layout.simple_spinner_item,returned);
                    aRetrun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    spReturn.setAdapter(aRetrun);

                    title.setText(userItemPojo.getName());
                        title.setTextColor(Color.parseColor("#000000"));

                        date.setText("Booked Date " + userItemPojo.getItemDate());
                        returnDate.setText("Returned " + userItemPojo.getReturnDate());
                        if (userItemPojo.getColor() != null) {
                            color.setText("Selected Color " + userItemPojo.getColor());
                        }
                        Glide.with(context)
                                .load(userItemPojo.getImageUri())
                                .into(image);


                        Button dialogButton = (Button) dialog.findViewById(R.id.btnOkayBooked);

                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();


                }
            });

            holder.ivStatusColor.setImageResource(R.drawable.color_profile2);

            if (userItemPojo.getBookingStatus()==null||userItemPojo.getBookingStatus().equalsIgnoreCase("Status"))
            {
                holder.ivStatusColor.setImageResource(R.drawable.color_profile);
                holder.tvProcessStatus.setText("Booked");
            }

            if(userItemPojo.getBookingStatus().equalsIgnoreCase("Booked"))
            {
                holder.ivStatusColor.setImageResource(R.drawable.color_profile);
                holder.tvProcessStatus.setText("Booked");
            }
            if(userItemPojo.getBookingStatus().equalsIgnoreCase("Waiting for approval"))
            {
                holder.ivStatusColor.setImageResource(R.drawable.color_profile1);
                holder.tvProcessStatus.setText("Waiting for approval");
            }
            if(userItemPojo.getBookingStatus().equalsIgnoreCase("Approved"))
            {
                holder.ivStatusColor.setImageResource(R.drawable.color_profile2);
                holder.tvProcessStatus.setText("Approved");
            }
            if(userItemPojo.getBookingStatus().equalsIgnoreCase("Pick Up"))
            {
                holder.ivStatusColor.setImageResource(R.drawable.color_profile3);
                holder.tvProcessStatus.setText("Pick Up");
            }



        }

        @Override
        public int getItemCount() {
            return (null != userItemPojos ? userItemPojos.size() : 0);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvItemDescr,tvProcessStatus,tvItemDate;
            LinearLayout llUserItem;
            ImageView ivStatusColor;



            public MyViewHolder(View itemView) {
                super(itemView);
                llUserItem= itemView.findViewById(R.id.llUserItemBooked);
                tvItemDescr = itemView.findViewById(R.id.tvItemTitleBooked);
                tvProcessStatus = itemView.findViewById(R.id.tvProcessBooked);
                ivStatusColor = itemView.findViewById(R.id.ivStatusColorBooked);
                tvItemDate = itemView.findViewById(R.id.tvItemDateBooked);



            }
        }
    }

