package a1ex9788.dadm.weathercomparer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import a1ex9788.dadm.weathercomparer.ui.forecast.ForecastFragment;
import a1ex9788.dadm.weathercomparer.ui.map.MapFragment;
import a1ex9788.dadm.weathercomparer.ui.places.PlacesFragment;
import a1ex9788.dadm.weathercomparer.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar tbNavigationDrawer;
    private DrawerLayout dlNavigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tbNavigationDrawer = findViewById(R.id.tb_navigation_drawer);
        setSupportActionBar(this.tbNavigationDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle(R.string.app_name);
        this.dlNavigationDrawer = findViewById(R.id.nd_layout);
        NavigationView nvNavigationDrawer = findViewById(R.id.nv_navigation_drawer);
        nvNavigationDrawer.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.dlNavigationDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.dlNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            this.dlNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.dlNavigationDrawer.closeDrawer(GravityCompat.START);
        Class<? extends Fragment> fragmentClass = null;
        if (item.getItemId() == R.id.navigation_forecast) {
            fragmentClass = ForecastFragment.class;
            getSupportActionBar().setTitle(R.string.title_forecast);
        } else if (item.getItemId() == R.id.navigation_places) {
            fragmentClass = PlacesFragment.class;
            getSupportActionBar().setTitle(R.string.title_places);
        } else if (item.getItemId() == R.id.navigation_map) {
            fragmentClass = MapFragment.class;
            getSupportActionBar().setTitle(R.string.title_map);
        } else if (item.getItemId() == R.id.navigation_settings) {
            fragmentClass = SettingsFragment.class;
            getSupportActionBar().setTitle(R.string.title_settings);
        }

        if (fragmentClass != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fcv_navigation_drawer, fragmentClass, null)
                    .commit();
            return true;
        }
        return false;
    }

}