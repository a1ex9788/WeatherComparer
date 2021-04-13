package a1ex9788.dadm.weathercomparer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import a1ex9788.dadm.weathercomparer.model.MapPlace;

@Dao
public interface RoomDao {

    @Insert
    void addPlace(MapPlace place);

    @Delete
    void deletePlace(MapPlace place);

    @Query("SELECT * FROM " + PlaceContract.MyTableEntry.PLACE_TABLE)
    List<MapPlace> getPlaces();

    @Query("SELECT * FROM " + PlaceContract.MyTableEntry.PLACE_TABLE +" WHERE " + PlaceContract.MyTableEntry.ID + " = :id")
    MapPlace searchPlace(String id);

    @Query("DELETE FROM " + PlaceContract.MyTableEntry.PLACE_TABLE)
    void clear();
}