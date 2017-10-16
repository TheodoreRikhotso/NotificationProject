package com.example.admin.notificationproject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<Catalog> catalogListCars;
    private RecyclerView ListViewCar;
    LinearLayoutManager layoutManager;
    private CatalogAdapter adapters;

    //search
//    private SearchView mSearchView;

    //FIREBASE CONNECTION
    private DatabaseReference databaseLaptops;
    private StorageReference mStorageReference;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

//        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarCar0) ;
        toolbar.setTitle("Cars");
        toolbar.setTitleTextColor(Color.WHITE);





        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//get A
        LandingScreen.ACYIVITY ="CAR";


        DatabaseReference databaseCatalo = FirebaseDatabase.getInstance().getReference("Cars");
        ListViewCar = (RecyclerView) findViewById(R.id.ListViewCar);

        catalogListCars = new ArrayList<>();

//        //search
//        mSearchView = (SearchView) findViewById(R.id.imageButton2);

        databaseCatalo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                catalogListCars.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    String  TAG ="MANI";
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    ListViewCar = (RecyclerView) findViewById(R.id.lvCar);
                    catalogListCars.add(catalog);
                    int numberOfColumns = 4;
                    ListViewCar.setLayoutManager(new GridLayoutManager(CarActivity.this, numberOfColumns));
//                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    adapters = new CatalogAdapter(CarActivity.this, catalogListCars);

                    ListViewCar.setHasFixedSize(true);
                    Log.d(TAG,catalog.getCatalogtitle()+"");
//                    search(mSearchView);



                    ListViewCar.setLayoutManager(layoutManager);

                    ListViewCar.setAdapter(adapters);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CarActivity.this, databaseError.getMessage(),Toast.LENGTH_LONG).show();
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




