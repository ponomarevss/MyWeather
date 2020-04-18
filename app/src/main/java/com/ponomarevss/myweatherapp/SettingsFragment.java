package com.ponomarevss.myweatherapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.ponomarevss.myweatherapp.Constants.CHOSEN_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.MAIN_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.PARCEL;
import static com.ponomarevss.myweatherapp.Constants.SETTINGS_FRAGMENT;


public class SettingsFragment extends Fragment {

    private Parcel parcel;
    private PlacesFragment placesFragment;
    private String chosenFragment;
    private CheckBox windCheckBox;
    private CheckBox humidityCheckBox;
    private CheckBox pressureCheckBox;

    static SettingsFragment newInstance(Parcel parcel, String string) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARCEL, parcel);
        args.putString(CHOSEN_FRAGMENT, string);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parcel = getArguments().getParcelable(PARCEL);
            chosenFragment = getArguments().getString(CHOSEN_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        windCheckBox = view.findViewById(R.id.wind_checkbox);
        humidityCheckBox = view.findViewById(R.id.humidity_checkbox);
        pressureCheckBox = view.findViewById(R.id.pressure_checkbox);

        windCheckBox.setChecked(parcel.isWindChecked());
        humidityCheckBox.setChecked(parcel.isHumidityChecked());
        pressureCheckBox.setChecked(parcel.isPressureChecked());

        //создание фрагмента задание места
        if(chosenFragment.equals(SETTINGS_FRAGMENT)) {
            replacePlacesFragment();
            if (!isLandscape) {
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(placesFragment);
                    fragmentTransaction.commit();
                }
            }
        }

        //кнопка возврата на главный фрагмент
        final ImageButton backToMainButton = view.findViewById(R.id.back_to_main_button);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateParcel();
                chosenFragment = MAIN_FRAGMENT;
                replaceMainFragment();
            }
        });
    }

    private void updateParcel() {
        parcel.setWindChecked(windCheckBox.isChecked());
        parcel.setHumidityChecked(humidityCheckBox.isChecked());
        parcel.setPressureChecked(pressureCheckBox.isChecked());
    }

    private void replaceMainFragment() {
        MainFragment fragment = MainFragment.newInstance(parcel, chosenFragment);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void replacePlacesFragment() {
        placesFragment = PlacesFragment.newInstance(parcel, chosenFragment);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.next_fragment_container, placesFragment);
        fragmentTransaction.commit();
    }
}
