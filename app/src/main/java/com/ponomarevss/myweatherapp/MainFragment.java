package com.ponomarevss.myweatherapp;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.HUMIDITY;
import static com.ponomarevss.myweatherapp.Constants.INDEX;
import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;
import static com.ponomarevss.myweatherapp.Constants.PLACE;
import static com.ponomarevss.myweatherapp.Constants.PRESSURE;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;
import static com.ponomarevss.myweatherapp.Constants.WIND;

public class MainFragment extends Fragment {

    private MiscFragment miscFragment;
    private ImageView background;
    private TextView placeTextView;

    static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackgroundView();
        setPlaceView(view);
        //TODO: установить данные о температуре и текущей погоде
        setDetailsView(view);
        setHourlyView(view);
        createMiscFragment();

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
    }

    private void createMiscFragment() {
        miscFragment = MiscFragment.newInstance(placeTextView.getText().toString());
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.next_fragment_container, miscFragment)
                    .commit();
        }
    }

    private void setHourlyView(@NonNull View view) {
        final String[] hours = getResources().getStringArray(R.array.hours);
        final LinearLayout hourlyLayout = view.findViewById(R.id.hourly_layout);
        LayoutInflater hoursInflater = getLayoutInflater();
        for (final String hour : hours) {
            View hourLayoutView = hoursInflater.inflate(R.layout.hour_layout, hourlyLayout, false);
            TextView hourTextView = hourLayoutView.findViewById(R.id.hour_value);
            hourTextView.setText(hour);
            hourlyLayout.addView(hourLayoutView);
        }
    }

    private void setDetailsView(@NonNull View view) {
//        assert getActivity() != null;
        final boolean[] details = getDetails(); //получаем були
        final TypedArray icons = getResources().obtainTypedArray(R.array.detail_icon);    //создаем массив иконок
        //TODO: создаем массив величин
        final String[] measureUnits = getResources().getStringArray(R.array.measure_unit);    //создаем массив единиц измерения
        LinearLayout detailsLayout = view.findViewById(R.id.details_layout);
        for (int i = 0; i <details.length ; i++) {
            if (details[i]) {
                //надуваем лейаут
                View detailLayout = getLayoutInflater().inflate(R.layout.detail_layout, detailsLayout, false);
                //устанавливаем иконку
                ImageView icon = detailLayout.findViewById(R.id.detail_icon);
                icon.setImageResource(icons.getResourceId(i, -1));
                //TODO: устанавливаем значение
                //устанавливаем единицу измерения
                TextView measureUnit = detailLayout.findViewById(R.id.detail_unit);
                measureUnit.setText(measureUnits[i]);
                detailsLayout.addView(detailLayout);
            }
        }
        icons.recycle();
    }

    private void toSettingsFragment() {
        SettingsFragment fragment = SettingsFragment.newInstance(true);
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .remove(miscFragment)
                    .commit();
        }
    }
    private void toPlacesFragment() {
        PlacesFragment fragment = PlacesFragment.newInstance(true);
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.next_fragment_container, fragment)
                    .remove(MainFragment.this)
                    .commit();
        }
    }

    private int getBackgroundIndex() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getInt(INDEX, INIT_INDEX);
    }

    private void setBackgroundView() {
        assert getActivity() != null;
        background = getActivity().findViewById(R.id.background);
        TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getBackgroundIndex() == INIT_INDEX) {
            background.setImageResource(R.drawable.background_default);
        } else {
            background.setImageResource(images.getResourceId(getBackgroundIndex(), -1));
        }
        background.setVisibility(View.VISIBLE);
        images.recycle();
    }

    private String getPlace() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getString(PLACE, SET_PLACE);
    }
    private void setPlaceView(View view) {
        placeTextView = view.findViewById(R.id.place);
        assert getActivity() != null;
        placeTextView.setText(getPlace());
    }

    private boolean[] getDetails() {
        assert getActivity() != null;
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        return new boolean[] {
                sharedPreferences.getBoolean(WIND, false),
                sharedPreferences.getBoolean(HUMIDITY, false),
                sharedPreferences.getBoolean(PRESSURE, false)
        };
    }
}
