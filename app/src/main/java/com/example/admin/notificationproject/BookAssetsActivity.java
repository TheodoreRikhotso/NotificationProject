package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BookAssetsActivity extends AppCompatActivity {

    ProfilePojo profilePojo;
    RecyclerView rvUserItems;
    List<UserItemPojo> userItemPojos;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_assets);
        Intent intent = getIntent();
        profilePojo = (ProfilePojo) intent.getSerializableExtra("select");
        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("Users/" + profilePojo.getLogId() + "/History");

        rvUserItems = (RecyclerView) findViewById(R.id.rvUserItemBooked);
        userItemPojos = new ArrayList<>();

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userItemPojos.clear();
                Toast.makeText(BookAssetsActivity.this, dataSnapshot.hasChildren()+"", Toast.LENGTH_SHORT).show();

                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    UserItemPojo item = catalogSnapshot.getValue(UserItemPojo.class);


                    date = item.getItemDate();
                    int numberOfColumns = 1;
                    item.getName();

                    rvUserItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
                    userItemPojos.add(item);
                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.d);
                    rvUserItems.addItemDecoration(itemDecoration);

                    //layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                }
                UserItemAdapter adapterss = new UserItemAdapter(BookAssetsActivity.this, userItemPojos);
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
                Toast.makeText(BookAssetsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
