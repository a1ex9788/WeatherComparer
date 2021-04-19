package a1ex9788.dadm.weathercomparer.webServices.places;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.webServices.ApiKeys;
import a1ex9788.dadm.weathercomparer.webServices.WebServicesHelper;

public class GooglePlaces {
    PlacesClient placesClient;

    public GooglePlaces(Context context) {
        this.placesClient = Places.createClient(context);
    }

    public String searchLocalityByNearby(double latitude, double longitude) throws Exception {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("maps.googleapis.com");
        uriBuilder.appendPath("maps");
        uriBuilder.appendPath("api");
        uriBuilder.appendPath("place");
        uriBuilder.appendPath("nearbysearch");
        uriBuilder.appendPath("json");
        uriBuilder.appendQueryParameter("location", latitude + "," + longitude);
        uriBuilder.appendQueryParameter("type", "locality");
        uriBuilder.appendQueryParameter("radius", "4000");
        uriBuilder.appendQueryParameter("key", ApiKeys.getGoogleApiKey());

        GooglePlace googlePlace = WebServicesHelper.getWebServiceAnswer(uriBuilder, GooglePlace.class);

        if (googlePlace == null || googlePlace.results == null || googlePlace.results.get(0) == null) {
            return null;
        }

        GooglePlace.GooglePlaceResult googlePlaceResult = googlePlace.results.get(0);

        if (googlePlaceResult.place_id == null || googlePlaceResult.name == null) {
            return null;
        }

        return googlePlaceResult.place_id;
    }

    public void getDetails(String id, OnSuccessListener onFounded) {
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS, Place.Field.LAT_LNG, Place.Field.UTC_OFFSET);
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(id, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener(onFounded);
    }
}

