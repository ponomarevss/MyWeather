package com.ponomarevss.myweatherapp;

import android.annotation.SuppressLint;
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

import static com.ponomarevss.myweatherapp.Constants.CHOSEN_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;
import static com.ponomarevss.myweatherapp.Constants.PARCEL;
import static com.ponomarevss.myweatherapp.Constants.PLACE_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.SETTINGS_FRAGMENT;

public class MainFragment extends Fragment {

    private Parcel parcel;
    private String chosenFragment;
    private MiscFragment miscFragment;
    private ImageView background;
    private TextView placeTextView;
    private LinearLayout windLayout;
    private LinearLayout humidityLayout;
    private LinearLayout pressureLayout;

    static MainFragment newInstance(Parcel parcel, String string) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARCEL, parcel);
        args.putString(CHOSEN_FRAGMENT, string);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        if (getArguments() != null) {
            parcel = getArguments().getParcelable(PARCEL);
            chosenFragment = getArguments().getString(CHOSEN_FRAGMENT);
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

        if (getActivity() == null) return;
        background = getActivity().findViewById(R.id.background);
        placeTextView = view.findViewById(R.id.place);
        windLayout = view.findViewById(R.id.wind_layout);
        humidityLayout = view.findViewById(R.id.humidity_layout);
        pressureLayout = view.findViewById(R.id.pressure_layout);

        //заполняем элементы вью данными из парсела
        @SuppressLint("Recycle") TypedArray images = getResources().obtainTypedArray(R.array.city_images);
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

        //создаем блок почасовой погоды
        final String[] hours = getResources().getStringArray(R.array.hours);
        final LinearLayout hourlyLayout = view.findViewById(R.id.hourly_layout);
        LayoutInflater hoursInflater = getLayoutInflater();

        for (final String hour : hours) {
            View hourLayoutView = hoursInflater.inflate(R.layout.hour_layout, hourlyLayout, false);
            TextView hourTextView = hourLayoutView.findViewById(R.id.hour_value);
            hourTextView.setText(hour);
            hourlyLayout.addView(hourLayoutView);
        }

        //создание фрагмента с доп.информацией: в портретной - снизу, в ландшафтной - справа
        miscFragment = MiscFragment.newInstance(placeTextView.getText().toString());
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.next_fragment_container, miscFragment);
        fragmentTransaction.commit();

        //переход на фрагмент настроек
        final ImageButton settingsImageButton = view.findViewById(R.id.settings_button);
        settingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //отключаем видимость фона
                background.setVisibility(View.GONE);

                prepareParcel();
                chosenFragment = SETTINGS_FRAGMENT;
                toSettingsFragment();
            }
        });

        //переход на фрагмент задания места
        placeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //отключаем видимость фона
                background.setVisibility(View.GONE);

                prepareParcel();
                chosenFragment = PLACE_FRAGMENT;
                toPlacesFragment();
            }
        });
    }

    private void prepareParcel() {
        parcel.setPlace(placeTextView.getText().toString());
        parcel.setWindChecked(isVisible(windLayout.getVisibility()));
        parcel.setHumidityChecked(isVisible(humidityLayout.getVisibility()));
        parcel.setPressureChecked(isVisible(pressureLayout.getVisibility()));
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
        SettingsFragment fragment = SettingsFragment.newInstance(parcel, chosenFragment);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.remove(miscFragment);
        fragmentTransaction.commit();
    }

    private void toPlacesFragment() {
        PlacesFragment fragment = PlacesFragment.newInstance(parcel, chosenFragment);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.next_fragment_container, fragment);
        fragmentTransaction.remove(MainFragment.this);
        fragmentTransaction.commit();
    }



}
