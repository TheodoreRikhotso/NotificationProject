package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private List<ProfilePojo> catalogList;
    private LinearLayoutManager layoutManager;
    private RecyclerView ListViewCatalog;
    private DatabaseReference databaseLaptops;

    private UsersAdapter adapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        catalogList = new ArrayList<ProfilePojo>();
        /**
         * LAPTOPS LISTVIEW
         */


//        mSearchView = (SearchView) findViewById(R.id.svLaptop);

        databaseLaptops = FirebaseDatabase.getInstance().getReference("AllUsers/");
        Query databaseIos = FirebaseDatabase.getInstance().getReference("AllUsers/");
        databaseLaptops.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogList.clear();

                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    ListViewCatalog = (RecyclerView) findViewById(R.id.lvUsers);
                    ProfilePojo catalog = catalogSnapshot.getValue(ProfilePojo.class);
                    catalogList.add(catalog);

                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    adapters = new UsersAdapter(UsersActivity.this, catalogList);
//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewCatalog.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    ListViewCatalog.setAdapter(adapters);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UsersActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
