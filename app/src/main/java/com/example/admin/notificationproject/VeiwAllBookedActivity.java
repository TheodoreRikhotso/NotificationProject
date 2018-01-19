package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VeiwAllBookedActivity extends AppCompatActivity {
    Firebase mFirebaseRef;
    List<UserItemPojo> userItemPojos;
    RecyclerView rvBooked;
    DatabaseReference dbRequest;
    private static String mainId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_all_booked);
         rvBooked =(RecyclerView)findViewById(R.id.rvBooked);

        dbRequest = FirebaseDatabase.getInstance().getReference("Users/");
        userItemPojos = new ArrayList<>();
        dbRequest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Log.i("MainActivity", child.getKey());
                    DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("Users/" + child.getKey() + "/History");
                    mainId =child.getKey();
                    databaseItems.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                                UserItemPojo item = catalogSnapshot.getValue(UserItemPojo.class);


                                int numberOfColumns = 1;



                                rvBooked.setLayoutManager(new GridLayoutManager(VeiwAllBookedActivity.this, numberOfColumns));
                                userItemPojos.add(item);
                                ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.d);
                                rvBooked.addItemDecoration(itemDecoration);

                                //layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                            }
                            AdminBookingsAdapter adapterss = new AdminBookingsAdapter(VeiwAllBookedActivity.this, userItemPojos,mainId);
                            Collections.reverse(userItemPojos);
//
                            adapterss.setHasStableIds(true);
                            adapterss.notifyDataSetChanged();

                            // rvUserItems.setLayoutManager(layoutManager);

                            rvBooked.setAdapter(adapterss);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
