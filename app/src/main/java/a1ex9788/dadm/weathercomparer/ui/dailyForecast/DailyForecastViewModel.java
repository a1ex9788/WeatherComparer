package a1ex9788.dadm.weathercomparer.ui.dailyForecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DailyForecastViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DailyForecastViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is daily forecast fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}