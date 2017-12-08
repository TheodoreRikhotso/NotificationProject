package com.example.admin.notificationproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Admin on 10/2/2017.
 */

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.MyViewHolder> {
    private Activity context;
    private List<UserItemPojo> userItemPojos;
    private Activity applicationContext;
    private boolean check =false;

    public UserItemAdapter(Activity context, List<UserItemPojo> userItemPojos) {
        this.context = context;
        this.userItemPojos = userItemPojos;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_model,null);

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
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String ids =user.getUid();
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

                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String ids = user.getUid();

                            DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("Users/" + ids + "/History/" + userItemPojo.getHistoryId());

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


                if(!userItemPojo.getBookingStatus().equalsIgnoreCase("Approved")|| !userItemPojo.getBookingStatus().equalsIgnoreCase("Pick Up") ){
                    final BottomSheetDialog dialog = new BottomSheetDialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.custom_dialog);
                    dialog.setTitle("Title");


                    TextView title = (TextView) dialog.findViewById(R.id.tvPopTitle);
                    TextView date = (TextView) dialog.findViewById(R.id.ivPopDate);
                    TextView returnDate = (TextView) dialog.findViewById(R.id.ivPopReturnDate);
                    TextView color = (TextView) dialog.findViewById(R.id.ivPopColor);
                    ImageView image = (ImageView) dialog.findViewById(R.id.ivPopImage);

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


                    Button dialogButton = (Button) dialog.findViewById(R.id.btnOkay);

                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else{
                    Snackbar.make(view,"You already approved you cannot deleted ",Snackbar.LENGTH_LONG).show();
                }

            }
        });

        holder.ivStatusColor.setImageResource(R.drawable.color_profile2);

        if (userItemPojo.getBookingStatus()==null)
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
            llUserItem= itemView.findViewById(R.id.llUserItem);
            tvItemDescr = itemView.findViewById(R.id.tvItemTitle);
            tvProcessStatus = itemView.findViewById(R.id.tvProcess);
            ivStatusColor = itemView.findViewById(R.id.ivStatusColor);
            tvItemDate = itemView.findViewById(R.id.tvItemDate);



        }
    }
}

