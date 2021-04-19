package a1ex9788.dadm.weathercomparer.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
            if (i == 3) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
        }
    }

}