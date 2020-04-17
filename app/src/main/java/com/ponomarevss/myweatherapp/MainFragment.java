package com.ponomarevss.myweatherapp;

import android.content.res.Configuration;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;
import static com.ponomarevss.myweatherapp.Constants.PARCEL;

/*
 * Главный фрагмент
 * в ПОРТРЕТНОМ:
 * 1. выводит доп.инфо вниз
 * 2. переходит на настройки по кнопке настроек
 * 3. переходит на выбор места по клику на текущее место
 *
 * в ЛАНДШАФТНОМ:
 * 1. гасит вью фрагмента с доп.инфо внизу и выводит фрагмент доп.настроек на правый контейнер
 * 2. размещает фрагмент настроек и фрагмент выбора места - по кнопке
 * 3. размещает фрагмент настроек и выбора места - по клику на текущее место
 *
 * обмен информацией:
 * с фрагментом настроек: булини ветра, влажности и давления
 * с фрагментом выбора места: стринг с названием места, инт индекса массива картинок
 *
 * */

public class MainFragment extends Fragment {

    private Parcel parcel;

    static MainFragment newInstance(Parcel parcel) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        if (getArguments() != null) {
            parcel = getArguments().getParcelable(PARCEL);
        }
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


        final ImageView background = getActivity().findViewById(R.id.background);
        final TextView placeTextView = view.findViewById(R.id.place);
        final LinearLayout windLayout = view.findViewById(R.id.wind_layout);
        final LinearLayout humidityLayout = view.findViewById(R.id.humidity_layout);
        final LinearLayout pressureLayout = view.findViewById(R.id.pressure_layout);

        TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        background.setVisibility(View.VISIBLE);
        if (parcel.getIndex() == INIT_INDEX) {
            background.setImageResource(R.drawable.background_default);
        } else {
            background.setImageResource(images.getResourceId(parcel.getIndex(), -1));
        }

        placeTextView.setText(parcel.getPlace());

        windLayout.setVisibility(visibility(parcel.isWindChecked()));
        humidityLayout.setVisibility(visibility(parcel.isHumidityChecked()));
        pressureLayout.setVisibility(visibility(parcel.isPressureChecked()));


        //создание фрагмента с доп.информацией
        final MiscFragment miscFragment = MiscFragment.newInstance(placeTextView.getText().toString());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.replace(R.id.misc_fragment_container, miscFragment);
        } else {
            View micsFragmentContainer = view.findViewById(R.id.misc_fragment_container);
            micsFragmentContainer.setVisibility(View.GONE);
            fragmentTransaction.replace(R.id.next_fragment_container, miscFragment);
        }
        fragmentTransaction.commit();

        //переход в настройки
        ImageButton settingsImageButton = view.findViewById(R.id.settings_button);
        settingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setVisibility(View.GONE);

                parcel.setPlace(placeTextView.getText().toString());
                parcel.setWindChecked(isVisible(windLayout.getVisibility()));
                parcel.setHumidityChecked(isVisible(humidityLayout.getVisibility()));
                parcel.setPressureChecked(isVisible(pressureLayout.getVisibility()));

                toSettingsFragment();
            }
        });

        //создаем блок почасовой погоды
        final String[] hours = getResources().getStringArray(R.array.hours);
        LinearLayout hourlyLayout = view.findViewById(R.id.hourly_layout);
        LayoutInflater hoursInflater = getLayoutInflater();

        for (final String hour : hours) {
            View hourLayoutView = hoursInflater.inflate(R.layout.hour_layout, hourlyLayout, false);
            TextView hourTextView = hourLayoutView.findViewById(R.id.hour_value);
            hourTextView.setText(hour);
            hourlyLayout.addView(hourLayoutView);
        }

    }

    private int visibility(Boolean b) {
        int visibility = View.GONE;
        if (b) visibility = View.VISIBLE;
        return visibility;
    }

    private boolean isVisible(int i) {
        boolean isVisible = false;
        if (i == View.VISIBLE) isVisible = true;
        return isVisible;
    }

    private void toSettingsFragment() {
        SettingsFragment fragment = SettingsFragment.newInstance(parcel);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }



}
