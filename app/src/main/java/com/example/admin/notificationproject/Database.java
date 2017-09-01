package com.example.admin.notificationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/1/2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final String TABLE_NOTIFICATION="table_notification";
    private static final String KEY_ID ="id";
    private static final String KEY_TITLE ="title";
    private static final String KEY_MSG="subject";
    private static final String KEY_DATE ="marks";


    private final String CREATE_TABLE_NOTIFICATION ="create table "
            + TABLE_NOTIFICATION + " ("
            + KEY_ID + " integer primary key autoincrement, "
            + KEY_TITLE + " text not null, "
            + KEY_MSG + " text ,"
            + KEY_DATE + " text);";
    private static final String DATABASE_NAME="notification.db";
    private static final int DATABASE_VERSION=1;
    private Context context;

    public Database(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF TABLE EXIST" +TABLE_NOTIFICATION);
        db.execSQL(CREATE_TABLE_NOTIFICATION);
    }
    public int addContact(TitlePojo titlePojo)
    {
        int id =0;

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();


        cv.put(KEY_TITLE, titlePojo.getTitle());
        cv.put(KEY_MSG, titlePojo.getMessage());
        cv.put(KEY_DATE, titlePojo.getDate());



        id =(int)db.insert(TABLE_NOTIFICATION, null,cv);

        return id;

    }

    public List<TitlePojo> getAllPeople()
    {
        String role = "student";
        List<TitlePojo> titlePojos = new ArrayList<TitlePojo>();
        SQLiteDatabase db =this.getWritableDatabase();
        String selectQuery =" SELECT * FROM " + TABLE_NOTIFICATION ;


        Cursor cursor =db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do
            {
                TitlePojo titlePojo = new TitlePojo();
                titlePojo.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                titlePojo.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                titlePojo.setMessage(cursor.getString(cursor.getColumnIndex(KEY_MSG)));
                titlePojo.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));





                titlePojos.add(titlePojo);

            }while(cursor.moveToNext());
        }
        return titlePojos;

    }
}
