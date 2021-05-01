package a1ex9788.dadm.weathercomparer.ui.settings;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.preference.PreferenceFragmentCompat;

import a1ex9788.dadm.weathercomparer.MainActivity;
import a1ex9788.dadm.weathercomparer.R;

public class SettingsFragment extends PreferenceFragmentCompat {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		// Creates the View to be shown from a Preference resource
		setPreferencesFromResource(R.xml.preferences, rootKey);
		setNavigationDrawerCheckedItem();
	}

	private void setNavigationDrawerCheckedItem() {
		for (int i = 0; i < 4; i++) {
			MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
			item.setChecked(i == 3);
		}
	}

}