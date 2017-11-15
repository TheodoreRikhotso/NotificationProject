package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PhoneActiviy extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView ListViewPhones;
    LinearLayoutManager layoutManager;
    List<PhonePojo> catalogListPhones;

    //search
//    private SearchView mSearchView;
    private PhoneAdapter adapters;
    private  PhonePojo catalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_activiy);
        LandingScreen.ACYIVITY ="Phone";
        //toobar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarPhone) ;
        toolbar.setTitle("Phones");

        LandingScreen.ACYIVITY ="PHONE";

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DatabaseReference databaseCataloo = FirebaseDatabase.getInstance().getReference("Devices/Phones/details");
        ListViewPhones = (RecyclerView) findViewById(R.id.lvPhones);


        catalogListPhones = new ArrayList<>();

//        //search
//        mSearchView = (SearchView) findViewById(R.id.svPhones);

        databaseCataloo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogListPhones.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                     catalog = catalogSnapshot.getValue(PhonePojo.class);
                    ListViewPhones = (RecyclerView) findViewById(R.id.lvPhones);

                    catalogListPhones.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                     adapters = new PhoneAdapter(PhoneActiviy.this, catalogListPhones);

//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewPhones.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    ListViewPhones.setAdapter(adapters);
                    ListViewPhones.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(PhoneActiviy.this, "c", Toast.LENGTH_SHORT).show();
                            PhonePojo c = catalog;
                            Intent intent = new Intent(getApplication(), PhoneDescriptionActivity
                                    .class);
                            intent.putExtra("select", c);
                            startActivity(intent);
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;

    }

            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapters.getFilter().filter(newText);
                return true;
            }
        }

