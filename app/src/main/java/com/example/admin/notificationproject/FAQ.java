package com.example.admin.notificationproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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

public class FAQ extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GenreAdapter mAdapter;
    private List<Genre> genres;
    List<Artist> artists;
    private List<Userdetails> userdetailses;
    private  DividerItemDecoration mDividerItemDecoration;

    Userdetails userdetails;
    DatabaseReference databaseCatalo;
    private  LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tbFQA) ;
        toolbar.setTitle("Frequently Asked Questions");
        toolbar.setTitleTextColor(Color.BLACK);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.BLACK);

        userdetailses = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        DatabaseReference databaseCatalo = FirebaseDatabase.getInstance().getReference("message");
        databaseCatalo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                genres = new ArrayList<>(userdetailses.size());


                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {

                    Userdetails userdetails = catalogSnapshot.getValue(Userdetails.class);
                    userdetailses.add(userdetails);




                    List<Artist> artists = new ArrayList<>();
                    artists.add(new Artist(userdetails.getAddress()));
                    genres.add(new Genre(userdetails.getEmail(), artists));
                   layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);


                    mRecyclerView.setLayoutManager(new LinearLayoutManager(FAQ.this));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.addItemDecoration(
                            new DividerItemDecoration(getApplicationContext(),layoutManager.getOrientation()));
                    mAdapter = new GenreAdapter(genres);
                    mRecyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mAdapter = new GenreAdapter(genres);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(FAQ.this));
//        mRecyclerView.setAdapter(mAdapter);
    }

    public void getGenres() {


        int num =userdetailses.size();
        genres = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {



            //genres.add(new Genre(ud.getEmail(), artists));
        }
    }

}
