package com.mohammad.lab15googlemap.database;

import android.database.Cursor;
import android.database.CursorWrapper;


import com.mohammad.lab15googlemap.MapsActivity;
import com.mohammad.lab15googlemap.OurLocation;

import java.util.Date;

/**
 * Created by user on 10/28/2015.
 */
public class LocationCursorWraper extends CursorWrapper {

    public LocationCursorWraper(Cursor cursor)
    {
        super(cursor);
    }

    public OurLocation getOurLocation()
    {
        String lat = getString(getColumnIndex(LocationDbSchema.LocationTable.Cols.LAT));
        String lng = getString(getColumnIndex(LocationDbSchema.LocationTable.Cols.LNG));
        String address = getString(getColumnIndex(LocationDbSchema.LocationTable.Cols.ADDRESS));

        OurLocation ol = new OurLocation(lat, lng, address);

        return ol;

    }

    public OurLocation getHistory()
    {
        String lat = getString(getColumnIndex(LocationDbSchema.LocationHistroyTable.Cols.LAT));
        String lng = getString(getColumnIndex(LocationDbSchema.LocationHistroyTable.Cols.LNG));
        String address = getString(getColumnIndex(LocationDbSchema.LocationHistroyTable.Cols.ADDRESS));
        long date = getLong(getColumnIndex(LocationDbSchema.LocationHistroyTable.Cols.DATE));

        OurLocation ol = new OurLocation(lat, lng, address,new Date(date));

        return ol;

    }
}
