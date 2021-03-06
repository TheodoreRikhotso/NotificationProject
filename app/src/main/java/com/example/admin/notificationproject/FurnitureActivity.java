package com.example.admin.notificationproject;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FurnitureActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView ListViewFurniture;
    private List<FurniturePojo> catalogListFurniture;
    LinearLayoutManager layoutManager;
    //search
//    private SearchView mSearchView;
    private FurnitureAdapter adapters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarFurniture) ;
        toolbar.setTitle("Accessories");
        LandingScreen.ACYIVITY ="FURN";
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Devices/Furniture/details");
        ListViewFurniture = (RecyclerView) findViewById(R.id.lvFurniture);

        catalogListFurniture = new ArrayList<>();

//        //search
//        mSearchView = (SearchView) findViewById(R.id.svFurniture);

        databaseCatalogg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogListFurniture.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    FurniturePojo catalog = catalogSnapshot.getValue(FurniturePojo.class);
                    ListViewFurniture = (RecyclerView) findViewById(R.id.lvFurniture);

                    catalogListFurniture.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                     adapters = new FurnitureAdapter(FurnitureActivity.this, catalogListFurniture);
                    ListViewFurniture.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    ListViewFurniture.setAdapter(adapters);
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




