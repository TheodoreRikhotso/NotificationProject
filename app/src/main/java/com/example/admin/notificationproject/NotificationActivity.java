package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private DatabaseReference databaseCatalog;
    private ListView lvMain;
    private List<NotificationPojo> titlePojos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        titlePojos = new ArrayList<>();

        databaseCatalog = FirebaseDatabase.getInstance().getReference("Notificationd");
        databaseCatalog.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titlePojos.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    lvMain = (ListView)findViewById(R.id.lvMain);
                    NotificationPojo catalog = catalogSnapshot.getValue(NotificationPojo.class);
                    titlePojos.add(catalog);

                    NotificationAdapter adaptery = new NotificationAdapter(NotificationActivity.this, titlePojos);
//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();


                    lvMain.setAdapter(adaptery);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        NotificationAdapter adapter = new NotificationAdapter(this,titlePojos);
//        lvMain = (ListView)findViewById(R.id.lvMain);
       // lvMain.setAdapter(adapter);


    }
}
