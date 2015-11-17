package com.mohammad.lab15googlemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mohammad.lab15googlemap.database.LocationCursorWraper;
import com.mohammad.lab15googlemap.database.LocationDbHelper;
import com.mohammad.lab15googlemap.database.LocationDbSchema;

import java.util.ArrayList;
import java.util.List;

import static com.mohammad.lab15googlemap.MapsActivity.*;

/**
 * Created by user on 11/12/2015.
 */
public class Manager {
    private static Manager manager;
    private Context context;
    private SQLiteDatabase database;


    private Manager(Context context)
    {
        this.context = context.getApplicationContext();
        database = new LocationDbHelper(this.context).getWritableDatabase();
    }



    public List<OurLocation> getHistoryLocations()
    {
        List<OurLocation> histories = new ArrayList<>();
        LocationCursorWraper locationCursorWraper = querryHistories(null, null);
        try
        {
            locationCursorWraper.moveToFirst();
            while (!locationCursorWraper.isAfterLast())
            {
                histories.add(locationCursorWraper.getHistory());
                locationCursorWraper.moveToNext();
            }

        }finally {
            locationCursorWraper.close();

        }

        return histories;
    }



    public void addHistory(OurLocation location)
    {
        ContentValues values = getContentValuesH(location);
        database.insert(LocationDbSchema.LocationHistroyTable.NAME, null , values);
    }



    public static Manager getManager(Context context)
    {
        if(manager == null)
        {
            manager = new Manager(context);
        }
        return manager;
    }



    private static ContentValues getContentValuesH(OurLocation location)
    {
        ContentValues values = new ContentValues();
        values.put(LocationDbSchema.LocationHistroyTable.Cols.LAT,location.getLat() );
        values.put(LocationDbSchema.LocationHistroyTable.Cols.LNG,location.getLng() );
        values.put(LocationDbSchema.LocationHistroyTable.Cols.ADDRESS,location.getAddress());
        values.put(LocationDbSchema.LocationHistroyTable.Cols.DATE,location.getDate().getTime());

 return values;
    }


    private LocationCursorWraper querryHistories(String whereClause,String [] whereArgs)
    {
        Cursor cursor = database.query(
                LocationDbSchema.LocationHistroyTable.NAME,
                null, // all Cols
                whereClause,
                whereArgs,
                null, //groupBY
                null, // having
                null  // orderBy
        );
        return new LocationCursorWraper(cursor);
    }

        public List<OurLocation> getLocations()
    {
        List<OurLocation> ourLocations = new ArrayList<>();
        LocationCursorWraper locationCursorWraper = querryLocations(null, null);
        try
        {
            locationCursorWraper.moveToFirst();
            while (!locationCursorWraper.isAfterLast())
            {
                ourLocations.add(locationCursorWraper.getOurLocation());
                locationCursorWraper.moveToNext();
            }

        }finally {
            locationCursorWraper.close();

        }

        return ourLocations;
    }

        public void addOurLocation(OurLocation location)
    {
        ContentValues values = getContentValues(location);
        database.insert(LocationDbSchema.LocationTable.NAME, null , values);
    }

        private static ContentValues getContentValues(OurLocation location)
    {
        ContentValues values = new ContentValues();
        values.put(LocationDbSchema.LocationTable.Cols.LAT,location.getLat() );
        values.put(LocationDbSchema.LocationTable.Cols.LNG,location.getLng() );
        values.put(LocationDbSchema.LocationTable.Cols.ADDRESS,location.getAddress());



        return values;
    }

    private LocationCursorWraper querryLocations(String whereClause,String [] whereArgs)
    {
        Cursor cursor = database.query(
                LocationDbSchema.LocationTable.NAME,
                null, // all Cols
                whereClause,
                whereArgs,
                null, //groupBY
                null, // having
                null  // orderBy
        );
        return new LocationCursorWraper(cursor);
    }

}
