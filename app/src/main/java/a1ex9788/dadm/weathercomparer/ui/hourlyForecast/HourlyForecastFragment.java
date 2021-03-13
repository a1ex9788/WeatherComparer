package a1ex9788.dadm.weathercomparer.ui.hourlyForecast;

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

public class HourlyForecastFragment extends Fragment {

    private HourlyForecastViewModel hourlyForecastViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hourlyForecastViewModel =
                new ViewModelProvider(this).get(HourlyForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);
        final TextView textView = root.findViewById(R.id.text_hourlyForecast);
        hourlyForecastViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}