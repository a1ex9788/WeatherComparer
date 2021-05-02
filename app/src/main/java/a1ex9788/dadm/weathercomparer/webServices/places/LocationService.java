package a1ex9788.dadm.weathercomparer.webServices.places;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService {

	private final FusedLocationProviderClient fusedLocationClient;

	public LocationService(Context context) {
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
	}

	public void getLocation(Context context, LocationCallback onFounded) {
		if (ContextCompat.checkSelfPermission(context,
				android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			LocationRequest request = new LocationRequest();
			request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			request.setInterval(1000);

			fusedLocationClient.requestLocationUpdates(request, onFounded, Looper.getMainLooper()).addOnSuccessListener(command -> {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						fusedLocationClient.removeLocationUpdates(onFounded);
					}
				}, 5000);

			});
		}

	}

}
