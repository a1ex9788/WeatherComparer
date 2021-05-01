package a1ex9788.dadm.weathercomparer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import a1ex9788.dadm.weathercomparer.model.MapPlace;

@Database(entities = MapPlace.class, version = 1)
abstract public class Room extends RoomDatabase {

    static private Room instance;

    public synchronized static Room getInstance(Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context, Room.class, "db").build();
        }
        return instance;
    }

    public abstract RoomDao room();

}
