package a1ex9788.dadm.weathercomparer.webServices;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WebServicesHelper {

    public static boolean hasInternetConnection() {
        // TODO: Check internet connection in all screens.
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
            throw new Exception("An error response code '" + responseCode + "' was obtained from the URL '" + url.toString() + "'.");
        }

        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();
        String answer;
        while ((answer = bufferedReader.readLine()) != null) {
            stringBuffer.append(answer);
        }

        T webServiceAnswer = convertWebServiceAnswer(stringBuffer.toString(), webServiceAnswerType);

        bufferedReader.close();
        inputStreamReader.close();
        connection.disconnect();

        if (webServiceAnswer == null) {
            throw new Exception("The response of the web service could not be parsed to the type '" + webServiceAnswerType + "'.");
        }

        return webServiceAnswer;
    }

    public static <T> T convertWebServiceAnswer(String webServiceAnswer, Type webServiceAnswerType) {
        Gson gson = new Gson();

        return gson.fromJson(webServiceAnswer, webServiceAnswerType);
    }

}
