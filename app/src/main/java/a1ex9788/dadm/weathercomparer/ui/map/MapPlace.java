package a1ex9788.dadm.weathercomparer.ui.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MapPlace extends BaseObservable {
    private String id;
    private LatLng latLng;
    private String name;
    private List<String> photos = new ArrayList();
    private TimeZone timeZone;

    public MapPlace(Place googlePlace){
        id = googlePlace.getId();
        latLng = googlePlace.getLatLng();
        name = googlePlace.getName();
        for (PhotoMetadata photoMetadata:googlePlace.getPhotoMetadatas()) {
            photos.add(photoMetadata.zza());
        }

        timeZone = getTimeZone(googlePlace);
    }



    @Bindable
    public String getId() {
        return this.id;
    }

    @Bindable
    public LatLng getLatLng() {
        return this.latLng;
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    @Bindable
    public List<String> getPhotos() {
        return this.photos;
    }

    @Bindable
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    @NotNull
    private TimeZone getTimeZone(Place googlePlace) {
        String timeZoneId = "GMT";
        int offset = googlePlace.getUtcOffsetMinutes();
        if ( offset > -1) timeZoneId += "+";
        int hours = offset / 60;
        int minutes = offset % 60;
        timeZoneId += hours;
        if(minutes > 0) timeZoneId += ":" + minutes;
        return TimeZone.getTimeZone(timeZoneId);
    }
}
