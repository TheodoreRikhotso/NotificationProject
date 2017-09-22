package com.example.admin.notificationproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    LinearLayoutManager layoutManager;;

    //FIREBASE CONNECTION
    private DatabaseReference databaseLaptops;
    private StorageReference mStorageReference;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        DatabaseReference databaseCatalo = FirebaseDatabase.getInstance().getReference("Cars");
        ListViewCar = (RecyclerView) findViewById(R.id.ListViewCar);
        Toast.makeText(CarActivity.this, "Car",Toast.LENGTH_LONG).show();

        catalogListCars = new ArrayList<>();

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
                    CatalogAdapter adapters = new CatalogAdapter(CarActivity.this, catalogListCars);

                    Log.d(TAG,catalog.getCatalogtitle()+"");

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
}
