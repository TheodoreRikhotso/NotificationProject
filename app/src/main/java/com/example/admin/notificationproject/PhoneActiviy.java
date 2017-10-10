package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

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
    List<Catalog> catalogListPhones;

    //search
//    private SearchView mSearchView;
    private PhoneAdapter adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_activiy);

        //toobar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarPhone) ;
        toolbar.setTitle("Phones");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DatabaseReference databaseCataloo = FirebaseDatabase.getInstance().getReference("Phones");
        ListViewPhones = (RecyclerView) findViewById(R.id.lvPhones);


        catalogListPhones = new ArrayList<>();

//        //search
//        mSearchView = (SearchView) findViewById(R.id.svPhones);

        databaseCataloo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogListPhones.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    ListViewPhones = (RecyclerView) findViewById(R.id.lvPhones);

                    catalogListPhones.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                     adapters = new PhoneAdapter(PhoneActiviy.this, catalogListPhones);

//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewPhones.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    ListViewPhones.setAdapter(adapters);
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
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
       // searchView.setOnQueryTextListener(this);
        return true;

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

//    private void search(SearchView searchView) {
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                adapters.getFilter().filter(newText);
//                return true;
//            }
//        });
//    }


}
