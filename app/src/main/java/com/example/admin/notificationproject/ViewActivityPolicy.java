package com.example.admin.notificationproject;

/**
 * Created by Admin on 09-Oct-17.
 */

public class ViewActivityPolicy {
//    FirebaseDatabase database;
//    DatabaseReference myRef;
//    List<Listdata> list;
//    RecyclerView recyclerview;
//    Button okBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view);
//
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("message");
//        recyclerview = (RecyclerView) findViewById(R.id.rview);
//
//        okBtn = (Button)findViewById(R.id.okBtn);
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                list = new ArrayList<>();
//                // StringBuffer stringbuffer = new StringBuffer();
//                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
//
//                    Userdetails userdetails = dataSnapshot1.getValue(Userdetails.class);
//                    Listdata listdata = new Listdata();
//                    String name=userdetails.getName();
//                    String email=userdetails.getEmail();
//                    String address=userdetails.getAddress();
//                    listdata.setName(name);
//                    listdata.setEmail(email);
//                    listdata.setAddress(address);
//                    list.add(listdata);
//                    // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();
//
//                }
//
//                RecyclerviewAdapter recycler = new RecyclerviewAdapter(list);
//                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(ViewActivity.this);
//                recyclerview.setLayoutManager(layoutmanager);
//                recyclerview.setItemAnimator( new DefaultItemAnimator());
//                recyclerview.setAdapter(recycler);
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                //  Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//        okBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

}
