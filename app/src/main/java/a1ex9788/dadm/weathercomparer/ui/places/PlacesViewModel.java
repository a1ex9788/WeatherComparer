package a1ex9788.dadm.weathercomparer.ui.places;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlacesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlacesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is places fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}