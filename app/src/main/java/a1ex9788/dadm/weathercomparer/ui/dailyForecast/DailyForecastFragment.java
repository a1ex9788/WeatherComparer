package a1ex9788.dadm.weathercomparer.ui.dailyForecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import a1ex9788.dadm.weathercomparer.R;

public class DailyForecastFragment extends Fragment {

    private DailyForecastViewModel dailyForecastViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyForecastViewModel =
                new ViewModelProvider(this).get(DailyForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_daily_forecast, container, false);
        final TextView textView = root.findViewById(R.id.text_dailyForecast);
        dailyForecastViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}