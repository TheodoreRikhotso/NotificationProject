package com.example.admin.notificationproject;

/**
 * Created by Admin on 8/30/2017.
 */

//public class FirebaseHelper {
//    DatabaseReference db;
//    Boolean saved;
//
//
//    ArrayList<Tour>  hotels= new ArrayList<>();
//    public FirebaseHelper(DatabaseReference db) {
//        this.db = db;
//    }
//
//    public Boolean save(Tour tour)
//    {
//        if(tour==null)
//        {
//            saved=false;
//        }else {
//            try {
//                db.child("Nofi").push().setValue(tour);
//                saved=true;
//
//            }catch (DatabaseException e)
//            {
//                e.printStackTrace();
//                saved=false;
//            }
//        }
//        return  saved;
//    }
//    //Fetch and fill  ArrayList
//    public void fetchData(DataSnapshot dataSnapshot)
//    {
//        hotels.clear();
//        for (DataSnapshot ds :dataSnapshot.getChildren())
//        {
//            Tour tour= ds.getValue(Tour.class);
//            hotels.add(tour);
//        }
//    }
//
//    public ArrayList<Tour> retrive() {
//        db.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//
//        });
//        return hotels;
//    }
//}

