package a1ex9788.dadm.weathercomparer.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.databinding.PlaceViewBinding;
import a1ex9788.dadm.weathercomparer.model.HourForecast;
import a1ex9788.dadm.weathercomparer.ui.map.MapPlace;
import a1ex9788.dadm.weathercomparer.webServices.forecasts.average.AverageWeatherForecast;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private List<MapPlace> places = new ArrayList<>();

    public PlaceAdapter(){ }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

           PlaceViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.place_view,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
        MapPlace place = places.get(position);
        holder.binding.setPlace(place);
        if(place.getPhoto() != null) {
            Picasso.get()
                    .load(place.getPhoto())
                    .into(holder.binding.ivPlace);
        }

        AverageWeatherForecast average = new AverageWeatherForecast(place.getLat(), place.getLng());

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HourForecast currentForecast = average.getHourlyForecast().get(0);
                            Log.d("forecast",currentForecast.toString());
                            holder.binding.setForecast(currentForecast);
                            //animatePlaceCardIn();
                        } catch (Exception error) {

                        }
                    }
                }
        ).start();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public MapPlace getPlaceAt(int position){
        return places.get(position);
    }

    public MapPlace removePlaceAt(int position){
        MapPlace place = places.remove(position);
        notifyItemRemoved(position);
        return place;
    }

    public void clearAll(){
        places.clear();
        notifyDataSetChanged();
    }

    public void setPlaces(List<MapPlace> places){
        this.places = places;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private PlaceViewBinding binding;

        public ViewHolder(@NonNull PlaceViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}