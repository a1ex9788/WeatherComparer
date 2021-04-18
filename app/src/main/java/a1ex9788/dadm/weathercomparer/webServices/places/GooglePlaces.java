package a1ex9788.dadm.weathercomparer.webServices.places;

import android.net.Uri;

import a1ex9788.dadm.weathercomparer.model.MapPlace;
import a1ex9788.dadm.weathercomparer.webServices.ApiKeys;
import a1ex9788.dadm.weathercomparer.webServices.WebServicesHelper;

public class GooglePlaces {

    public MapPlace searchLocalityByNearby(double latitude, double longitude) throws Exception {
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

        return convertToPlaceStandard(googlePlace, latitude, longitude);
    }

    private MapPlace convertToPlaceStandard(GooglePlace googlePlace, double latitude, double longitude) {
        if (googlePlace == null || googlePlace.results == null || googlePlace.results.get(0) == null) {
            return null;
        }

        GooglePlace.GooglePlaceResult googlePlaceResult = googlePlace.results.get(0);

        if (googlePlaceResult.place_id == null || googlePlaceResult.name == null) {
            return null;
        }

        return new MapPlace(
                googlePlaceResult.place_id,
                googlePlaceResult.name,
                latitude,
                longitude,
                googlePlaceResult.photos == null || googlePlaceResult.photos.get(0) == null
                        ? null : googlePlaceResult.photos.get(0).photo_reference);
    }

}

