package com.ponomarevss.myweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import static com.ponomarevss.myweatherapp.Constants.PLACE;

public class MiscFragment extends Fragment {

    private String place;

    static MiscFragment newInstance(String place) {
        MiscFragment fragment = new MiscFragment();
        Bundle args = new Bundle();
        args.putString(PLACE, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            place = getArguments().getString(PLACE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_misc, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //создаем блок трехдневного прогноза
        final String[] days = getResources().getStringArray(R.array.days);
        LinearLayout threeDaysForecastLayout = view.findViewById(R.id.three_days_forecast_layout);
        LayoutInflater daysInflater = getLayoutInflater();

        for (final String day : days) {
            View dayLayoutView = daysInflater.inflate(R.layout.day_layout, threeDaysForecastLayout, false);
            TextView hourTextView = dayLayoutView.findViewById(R.id.day_text);
            hourTextView.setText(day);
            threeDaysForecastLayout.addView(dayLayoutView);
        }

        //кнопка перехода на сайт
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
                String url = getResources().getString(R.string.url) + cityHm.get(place);
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
}
