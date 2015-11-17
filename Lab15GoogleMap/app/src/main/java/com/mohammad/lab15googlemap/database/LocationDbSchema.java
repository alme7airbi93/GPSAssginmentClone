package com.mohammad.lab15googlemap.database;

/**
 * Created by user on 10/28/2015.
 */
public class LocationDbSchema {

    public static final class LocationTable
    {
        public static final String NAME = "savedlocation";
        public static final class Cols
        {
            public static final String LAT = "lat";
            public static final String LNG = "lng";
            public static final String ADDRESS = "address";

        }
    }

    public static final class LocationHistroyTable
    {
        public static final String NAME = "locationHistory";
        public static final class Cols
        {
            public static final String LAT = "lat";
            public static final String LNG = "lng";
            public static final String ADDRESS = "address";
            public static final String DATE = "date";

        }
    }
}
