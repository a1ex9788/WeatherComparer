package a1ex9788.dadm.weathercomparer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a1ex9788.dadm.weathercomparer.R;

public class CustomRecyclerAdapterMoreInfo extends RecyclerView.Adapter<CustomRecyclerAdapterMoreInfo.ViewHolder> {

	private final List<MoreInfo> list;

	public CustomRecyclerAdapterMoreInfo(List<MoreInfo> list) {
		this.list = list;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_layout, parent, false);
		CustomRecyclerAdapterMoreInfo.ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
		//holder.tvInfoTitle.setText(list.get(position).getMoreInfoTitle());
		//holder.tvInfoValue.setText(list.get(position).getMoreInfoValue());
		//holder.image.setImageResource(list.get(position).getImage());
	}

	@Override
	public int getItemCount() {
		return this.list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView tvInfoTitle, tvInfoValue;
		public ImageView image;

		public ViewHolder(View view) {
			super(view);
			this.tvInfoTitle = view.findViewById(R.id.tvInfoTitle);
			this.tvInfoValue = view.findViewById(R.id.tvInfoValue);
			this.image = view.findViewById(R.id.ivImage);
		}

	}

}