package com.example.admin.notificationproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity {

    private List<Catalog> catalogListCars;
    private RecyclerView ListViewCar;
    LinearLayoutManager layoutManager;
    private CatalogAdapter adapters;
    //search
    private SearchView mSearchView;

    //FIREBASE CONNECTION
    private DatabaseReference databaseLaptops;
    private StorageReference mStorageReference;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarCar) ;
        toolbar.setTitle("Cars");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        DatabaseReference databaseCatalo = FirebaseDatabase.getInstance().getReference("Cars");
        ListViewCar = (RecyclerView) findViewById(R.id.ListViewCar);
        Toast.makeText(CarActivity.this, "Car",Toast.LENGTH_LONG).show();

        catalogListCars = new ArrayList<>();
        //search
        mSearchView = (SearchView) findViewById(R.id.imageButton2);

        databaseCatalo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(CarActivity.this, "stop",Toast.LENGTH_SHORT).show();
                catalogListCars.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    String  TAG ="MANI";
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    ListViewCar = (RecyclerView) findViewById(R.id.lvCar);
                    catalogListCars.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    adapters = new CatalogAdapter(CarActivity.this, catalogListCars);

                    ListViewCar.setHasFixedSize(true);
                    Log.d(TAG,catalog.getCatalogtitle()+"");
                    search(mSearchView);
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


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapters.getFilter().filter(newText);
                return true;
            }
        });
    }

}
