package com.ponomarevss.myweatherapp;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.ponomarevss.myweatherapp.Constants.INDEX;
import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;


public class MainActivity extends BaseActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener bottomMenuListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MainFragment mainFragment = MainFragment.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, mainFragment)
                            .commit();
                    return true;
                case R.id.navigation_place:
                    PlacesFragment placesFragment = PlacesFragment.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, placesFragment)
                            .commit();
                        return true;
                case R.id.navigation_settings:
                    SettingsFragment settingsFragment = SettingsFragment.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, settingsFragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomMenuListener);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
        setBackgroundView();

/*
        if (savedInstanceState != null) return;
        MainFragment fragment = MainFragment.newInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        //переход на фрагмент настроек
        final ImageButton settingsImageButton = view.findViewById(R.id.settings_button);
        settingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setVisibility(View.GONE);
                toSettingsFragment();
            }
        });

        //переход на фрагмент задания места
        placeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setVisibility(View.GONE);
                toPlacesFragment();
            }
        });
*/

    }

    private int getBackgroundIndex() {
        return getPreferences(MODE_PRIVATE).getInt(INDEX, INIT_INDEX);
    }

    void setBackgroundView() {
        ImageView background = findViewById(R.id.background);
        TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getBackgroundIndex() == INIT_INDEX) {
            background.setImageResource(R.drawable.background_default);
        } else {
            background.setImageResource(images.getResourceId(getBackgroundIndex(), -1));
        }
        background.setVisibility(View.VISIBLE);
        images.recycle();
    }
}
