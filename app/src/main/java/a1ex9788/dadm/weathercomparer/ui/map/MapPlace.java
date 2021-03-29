package a1ex9788.dadm.weathercomparer.ui.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapPlace extends BaseObservable {
    private String id;
    private LatLng latLng;
    private String name;
    private List<String> photos = new ArrayList();

    public MapPlace(Place googlePlace){
        id = googlePlace.getId();
        latLng = googlePlace.getLatLng();
        name = googlePlace.getName();
        for (PhotoMetadata photoMetadata:googlePlace.getPhotoMetadatas()) {
            photos.add(photoMetadata.zza());
        }
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

}
