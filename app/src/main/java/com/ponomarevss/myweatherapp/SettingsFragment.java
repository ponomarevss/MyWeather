package com.ponomarevss.myweatherapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.ponomarevss.myweatherapp.Constants.PARCEL;


public class SettingsFragment extends Fragment {

    private Parcel parcel;

    private TextView placeTextView;
    private CheckBox windCheckBox;
    private CheckBox humidityCheckBox;
    private CheckBox pressureCheckBox;

    static SettingsFragment newInstance(Parcel parcel) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

//    private void toSettingsFragment() {
//        SettingsFragment fragment = SettingsFragment.newInstance(parcel);
//        FragmentManager fragmentManager = getFragmentManager();
//        if (fragmentManager == null) return;
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parcel = getArguments().getParcelable(PARCEL);
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

        final TextView arrow = view.findViewById(R.id.arrow);

        //создание фрагмента задание места
        if (isLandscape) {
            arrow.setVisibility(View.GONE);
            toPlacesFragment(R.id.next_fragment_container);
        }

        //переход на фрагмент задания места
        placeTextView = view.findViewById(R.id.places_screen_button);
        placeTextView.setText(parcel.getPlace());
        if (!isLandscape) {
            placeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateParcel();
                    toPlacesFragment(R.id.fragment_container);
                }
            });
        }

        //кнопка возврата на MainActivity
        final ImageButton backToMainButton = view.findViewById(R.id.back_to_main_button);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateParcel();
                toMainFragment();
            }
        });
    }

    private void updateParcel() {
        parcel.setPlace(placeTextView.getText().toString());
        parcel.setWindChecked(windCheckBox.isChecked());
        parcel.setHumidityChecked(humidityCheckBox.isChecked());
        parcel.setPressureChecked(pressureCheckBox.isChecked());
    }

    private void toMainFragment() {
        MainFragment fragment = MainFragment.newInstance(parcel);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void toPlacesFragment(int p) {
        PlacesFragment fragment = PlacesFragment.newInstance(parcel);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(p, fragment);
        fragmentTransaction.commit();
    }
}
