package a1ex9788.dadm.weathercomparer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.weathercomparer.R;
import a1ex9788.dadm.weathercomparer.databinding.PlaceViewBinding;
import a1ex9788.dadm.weathercomparer.ui.map.MapPlace;


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