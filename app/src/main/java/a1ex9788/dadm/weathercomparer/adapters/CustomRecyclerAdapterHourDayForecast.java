package a1ex9788.dadm.weathercomparer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import a1ex9788.dadm.weathercomparer.R;

public class CustomRecyclerAdapterHourDayForecast extends RecyclerView.Adapter<CustomRecyclerAdapterHourDayForecast.ViewHolder> {

    private List<HourDayForecast> list;

    public CustomRecyclerAdapterHourDayForecast(List<HourDayForecast> list){
        this.list = list;
    }

    @NonNull
    @Override
    public CustomRecyclerAdapterHourDayForecast.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_today_element, parent, false);
        CustomRecyclerAdapterHourDayForecast.ViewHolder holder = new CustomRecyclerAdapterHourDayForecast.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapterHourDayForecast.ViewHolder holder, final int position) {
        holder.tvHour.setText(list.get(position).getMoreInfoTitle());
        holder.tvTemperature.setText(list.get(position).getMoreInfoValue());
        holder.lavWeatherIcon.setAnimationFromUrl(list.get(position).getLottieAnimationView());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHour, tvTemperature;
        public LottieAnimationView lavWeatherIcon;

        public ViewHolder(View view){
            super(view);
            this.tvHour = (TextView) view.findViewById(R.id.tvHour);
            this.tvTemperature = (TextView) view.findViewById(R.id.tvTemp);
            this.lavWeatherIcon = (LottieAnimationView) view.findViewById(R.id.lavWeatherIcon);
        }
    }
}
