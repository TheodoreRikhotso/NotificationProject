package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LaptopActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<Catalog> catalogList;
    private LinearLayoutManager layoutManager;
    private RecyclerView ListViewCatalog;
      private DatabaseReference databaseLaptops;

    //search
//    private SearchView mSearchView;
    private LaptopAdapter adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        LandingScreen.ACYIVITY ="Laptop";
        //toobar
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarLaptop) ;
//        toolbar.setTitle("Laptops");
//
//        LandingScreen.ACYIVITY ="LAP";
//
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        catalogList = new ArrayList<Catalog>();
        /**
         * LAPTOPS LISTVIEW
         */

//        mSearchView = (SearchView) findViewById(R.id.svLaptop);

        databaseLaptops = FirebaseDatabase.getInstance().getReference("Laptops");
        databaseLaptops.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogList.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    ListViewCatalog = (RecyclerView) findViewById(R.id.lvLaptop);
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    catalogList.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                     adapters = new LaptopAdapter(LaptopActivity.this, catalogList);
//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewCatalog.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    ListViewCatalog.setAdapter(adapters);
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
