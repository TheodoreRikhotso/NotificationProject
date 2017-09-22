package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FurnitureActivity extends AppCompatActivity {
    private RecyclerView ListViewFurniture;
    private List<Catalog> catalogListFurniture;
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);

        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Furniture");
        ListViewFurniture = (RecyclerView) findViewById(R.id.lvFurniture);

        catalogListFurniture = new ArrayList<>();


        databaseCatalogg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogListFurniture.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    ListViewFurniture = (RecyclerView) findViewById(R.id.lvFurniture);

                    catalogListFurniture.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    CatalogAdapter adapterr = new CatalogAdapter(FurnitureActivity.this, catalogListFurniture);

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
