package a1ex9788.dadm.weathercomparer.ui.hourlyForecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HourlyForecastViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HourlyForecastViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hourly forecast fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}