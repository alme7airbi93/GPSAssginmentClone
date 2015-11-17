package com.mohammad.lab15googlemap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.mohammad.lab15googlemap.database.LocationDbSchema.*;


/**
 * Created by user on 10/28/2015.
 */
public class LocationDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "locations.db";

    public LocationDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ LocationTable.NAME+"(_id integer primary key autoincrement, "+
                LocationTable.Cols.LAT+ ","  +
                LocationTable.Cols.LNG + ","+
                LocationTable.Cols.ADDRESS +")");

        db.execSQL("create table "+ LocationHistroyTable.NAME+"(_id integer primary key autoincrement, "+
                LocationHistroyTable.Cols.LAT+ ","  +
                LocationHistroyTable.Cols.LNG + ","+
                LocationHistroyTable.Cols.ADDRESS + ","+
                LocationHistroyTable.Cols.DATE +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
