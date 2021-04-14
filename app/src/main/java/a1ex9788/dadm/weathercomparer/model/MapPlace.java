package a1ex9788.dadm.weathercomparer.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.TimeZone;

@Entity(tableName = "places")
public class MapPlace  {

    @NonNull
    @PrimaryKey
    private String id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "lat")
    private double lat;
    @NonNull
    @ColumnInfo(name = "lng")
    private double lng;
    @ColumnInfo(name = "photo")
    private String photo;
    @NonNull
    @ColumnInfo(name = "timeZone")
    private String timeZone;

    public MapPlace() {
    }

    public MapPlace(Place googlePlace) {
        id = googlePlace.getId();
        lat = googlePlace.getLatLng().latitude;
        lng = googlePlace.getLatLng().longitude;
        name = googlePlace.getName();
        Random rand = new Random();
        List<PhotoMetadata> photos = googlePlace.getPhotoMetadatas();
        if (photos != null && !photos.isEmpty()) {
            photo = "https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyAiKQz6mYGVdAYfIWDxkTiOIa0x86e2ntA&maxheight=200&photoreference=" + photos.get(rand.nextInt(photos.size())).zza();
        }
        timeZone = getTimeZone(googlePlace).getDisplayName();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @NotNull
    private TimeZone getTimeZone(Place googlePlace) {
        String timeZoneId = "GMT";
        int offset = googlePlace.getUtcOffsetMinutes();
        if (offset > -1) {
            timeZoneId += "+";
        }
        int hours = offset / 60;
        int minutes = offset % 60;
        timeZoneId += hours;
        if (minutes > 0) {
            timeZoneId += ":" + minutes;
        }
        return TimeZone.getTimeZone(timeZoneId);
    }

    public class PlaceResponse {

        public List results;

    }

}