package a1ex9788.dadm.weathercomparer.db;

import android.provider.BaseColumns;

public class PlaceContract {

    public PlaceContract(){}

    static class MyTableEntry implements BaseColumns {

        static final String PLACE_TABLE = "places";
        static final String ID = "id";
        static final String NAME = "name";
        static final String LAT = "lat";
        static final String LNG = "lng";
        static final String PHOTOS = "photos";
        static final String TIME_ZONE = "time_zone";
    }
}