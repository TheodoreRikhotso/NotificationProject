package com.example.admin.notificationproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LaptopActivity extends AppCompatActivity {

    private List<Catalog> catalogList;
    private LinearLayoutManager layoutManager;
    private RecyclerView ListViewCatalog;
      private DatabaseReference databaseLaptops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        //toobar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarLaptop) ;
        toolbar.setTitle("Laptops");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        catalogList = new ArrayList<Catalog>();
        /**
         * LAPTOPS LISTVIEW
         */
        databaseLaptops = FirebaseDatabase.getInstance().getReference("Laptops");
        databaseLaptops.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogList.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    ListViewCatalog = (RecyclerView) findViewById(R.id.lvLaptop);
                    Catalog catalog = catalogSnapshot.getValue(Catalog.class);
                    catalogList.add(catalog);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    CatalogAdapter adaptery = new CatalogAdapter(LaptopActivity.this, catalogList);
//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    ListViewCatalog.setLayoutManager(layoutManager);

                    ListViewCatalog.setAdapter(adaptery);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
