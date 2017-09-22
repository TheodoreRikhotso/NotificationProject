package com.example.admin.notificationproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    RecyclerView ListViewCatalog,ListViewPhones,ListViewCar ,ListViewFurniture ;
    List<Catalog> catalogList;
    LinearLayoutManager layoutManager;
    List<Catalog> catalogLists,catalogListCars,catalogListFurniture, catalogListPhones;
    DatabaseReference databaseCatalog;



    //FIREBASE CONNECTION
    private DatabaseReference databaseLaptops;
    private StorageReference mStorageReference;

    private ProgressDialog mDialog;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        catalogList = new ArrayList<>();


        /**
         * LAPTOPS LISTVIEW
         */
        databaseCatalog = FirebaseDatabase.getInstance().getReference("Laptops");
        databaseCatalog.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogList.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    ListViewCatalog = (RecyclerView) findViewById(R.id.ListViewCatalog);
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    catalogList.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    CatalogAdapter adaptery = new CatalogAdapter(CatalogActivity.this, catalogList);
//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewCatalog.setLayoutManager(layoutManager);

                    ListViewCatalog.setAdapter(adaptery);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /**
         * PHONES LISTVIEW
         */
        DatabaseReference databaseCataloo = FirebaseDatabase.getInstance().getReference("Phones");
        ListViewPhones = (RecyclerView) findViewById(R.id.ListViewPhones);


        catalogListPhones = new ArrayList<>();

        databaseCataloo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogListPhones.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    ListViewPhones = (RecyclerView) findViewById(R.id.ListViewPhones);

                    catalogListPhones.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    CatalogAdapter adapterss = new CatalogAdapter(CatalogActivity.this, catalogListPhones);

//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewPhones.setLayoutManager(layoutManager);

                    ListViewPhones.setAdapter(adapterss);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /**
         * CARSS LISTVIEW
         */
        DatabaseReference databaseCatalo = FirebaseDatabase.getInstance().getReference("Cars");
        ListViewCar = (RecyclerView) findViewById(R.id.ListViewCar);


        catalogListCars = new ArrayList<>();

        databaseCatalo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogListCars.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    ListViewCar = (RecyclerView) findViewById(R.id.ListViewCar);

                    catalogListCars.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    CatalogAdapter adapters = new CatalogAdapter(CatalogActivity.this, catalogListCars);

//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewCar.setLayoutManager(layoutManager);

                    ListViewCar.setAdapter(adapters);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /**
         * FURNITURE LISTVIEW
         */
        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Furniture");
        ListViewFurniture = (RecyclerView) findViewById(R.id.ListViewFurniture);

        catalogListFurniture = new ArrayList<>();


        databaseCatalogg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogListFurniture.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    ListViewFurniture = (RecyclerView) findViewById(R.id.ListViewFurniture);

                    catalogListFurniture.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    CatalogAdapter adapterr = new CatalogAdapter(CatalogActivity.this, catalogListFurniture);

//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewFurniture.setLayoutManager(layoutManager);

                    ListViewFurniture.setAdapter(adapterr);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}





