package com.ponomarevss.myweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.HUMIDITY;
import static com.ponomarevss.myweatherapp.Constants.PLACE;
import static com.ponomarevss.myweatherapp.Constants.PRESSURE;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;
import static com.ponomarevss.myweatherapp.Constants.WIND;

public class MainFragment extends Fragment {

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
        setPlaceView(view);
        //TODO: установить данные о температуре и текущей погоде
        setDetailsView(view);
        setHourlyView(view);
        goToBrowserButton(view);
        setThreeDaysForecast(view);
//        createMiscFragment();


    }

    private void setThreeDaysForecast(@NonNull View view) {
        final String[] days = getResources().getStringArray(R.array.days);
        LinearLayout threeDaysForecastLayout = view.findViewById(R.id.three_days_forecast_layout);
        LayoutInflater daysInflater = getLayoutInflater();

        for (final String day : days) {
            View dayLayoutView = daysInflater.inflate(R.layout.day_layout, threeDaysForecastLayout, false);
            TextView hourTextView = dayLayoutView.findViewById(R.id.day_text);
            hourTextView.setText(day);
            threeDaysForecastLayout.addView(dayLayoutView);
        }
    }

    private void goToBrowserButton(@NonNull View view) {
        TextView moreInfo = view.findViewById(R.id.more_info);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] city = getResources().getStringArray(R.array.cities);
                String[] cityUrl = getResources().getStringArray(R.array.cities_url);
                Map<String, String> cityHm= new HashMap<>();
                for (int i = 0; i < city.length; i++) {
                    cityHm.put(city[i], cityUrl[i]);
                }
                assert getActivity() != null;
                String url = getResources().getString(R.string.url) + cityHm.get(getPlace());
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Context context = getContext();
                if (context == null) return;
                ActivityInfo activityInfo = intent.resolveActivityInfo(context.getPackageManager(), intent.getFlags());
                if (activityInfo != null) {
                    startActivity(intent);
                }
            }
        });
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

/*
    private int getBackgroundIndex() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getInt(INDEX, INIT_INDEX);
    }

    private void setBackgroundView() {
        assert getActivity() != null;
        ImageView background = getActivity().findViewById(R.id.background);
        TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getBackgroundIndex() == INIT_INDEX) {
            background.setImageResource(R.drawable.background_default);
        } else {
            background.setImageResource(images.getResourceId(getBackgroundIndex(), -1));
        }
        background.setVisibility(View.VISIBLE);
        images.recycle();
    }
*/

    private String getPlace() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getString(PLACE, SET_PLACE);
    }
    private void setPlaceView(View view) {
        TextView placeTextView = view.findViewById(R.id.place);
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
