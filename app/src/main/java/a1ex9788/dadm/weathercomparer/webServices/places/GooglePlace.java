package a1ex9788.dadm.weathercomparer.webServices.places;

import java.util.List;

// Attributes should be private and be accessed via getters but they are not in order to simplify
// the class and its access.
class GooglePlace {

	public List<GooglePlaceResult> results;

	class GooglePlaceResult {

		public String place_id;
		public String name;
		public List<Photo> photos;

		class Photo {

			public String photo_reference;

		}

	}

}
