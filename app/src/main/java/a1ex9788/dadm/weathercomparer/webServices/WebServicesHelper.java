package a1ex9788.dadm.weathercomparer.webServices;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WebServicesHelper {

    public static boolean hasInternetConnection() {
        /*boolean result = false;

        ConnectivityManager manager = (ConnectivityManager) reference.get().getSystemService(CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT > 22) {
            final Network activeNetwork = manager.getActiveNetwork();
            if (activeNetwork != null) {
                final NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(activeNetwork);
                result = networkCapabilities != null && (
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            }
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            result = ((info != null) && (info.isConnected()));
        }

        return result;*/
        return true;
    }

    public static <T> T getWebServiceAnswer(Uri.Builder uriBuilder, Type webServiceAnswerType) throws Exception {
        URL url = new URL(uriBuilder.build().toString());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("An error response code '" + responseCode + "' was obtained from the URL: " + url.getPath());
        }

        Gson gson = new Gson();
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        T webServiceAnswer = gson.fromJson(inputStreamReader, webServiceAnswerType);

        inputStreamReader.close();
        connection.disconnect();

        return webServiceAnswer;
    }

}
