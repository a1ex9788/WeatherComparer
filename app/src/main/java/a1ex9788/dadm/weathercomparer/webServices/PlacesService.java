package a1ex9788.dadm.weathercomparer.webServices;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;


import a1ex9788.dadm.weathercomparer.ui.map.MapPlace;

public class PlacesService {

    private final String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key" + ApiKeys.GOOGLE;
    String example = "location=39.5318061,-0.3505977&key=AIzaSyAiKQz6mYGVdAYfIWDxkTiOIa0x86e2ntA&radius=1000&type=locality";

    public void searchLocalityByNerby(LatLng location) throws Exception {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("maps.googleapis.com");
        uriBuilder.appendPath("maps");
        uriBuilder.appendPath("api");
        uriBuilder.appendPath("place");
        uriBuilder.appendPath("nearbysearch");
        uriBuilder.appendPath("json");
        uriBuilder.appendQueryParameter("key", ApiKeys.GOOGLE);
        uriBuilder.appendQueryParameter("location", location.latitude + "," + location.longitude);
        uriBuilder.appendQueryParameter("type", "locality");
        uriBuilder.appendQueryParameter("radius", "4000");

        Log.d("map",uriBuilder.toString());

        MapPlace.PlaceResponse placeResponse = WebServicesHelper.getWebServiceAnswer(uriBuilder, MapPlace.PlaceResponse.class);

        Log.d("map",placeResponse.toString());
  }

}

