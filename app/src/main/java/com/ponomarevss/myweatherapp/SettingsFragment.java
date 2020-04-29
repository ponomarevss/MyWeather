package com.ponomarevss.myweatherapp;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.HUMIDITY;
import static com.ponomarevss.myweatherapp.Constants.PRESSURE;
import static com.ponomarevss.myweatherapp.Constants.SETTINGS_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.WIND;


public class SettingsFragment extends Fragment {

    private PlacesFragment placesFragment;
    private boolean isPrimal;

    static SettingsFragment newInstance(Boolean isPrimal) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putBoolean(SETTINGS_FRAGMENT, isPrimal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isPrimal = getArguments().getBoolean(SETTINGS_FRAGMENT);
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
        setWindCheckBox(view);
        setHumidityCheckBox(view);
        setPressureCheckBox(view);
        setThemeSwitch(view);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        //создание фрагмента задания места в ландшафтной ориентации
        if(isPrimal) {
            setPlacesFragment();
            if (!isLandscape) {
                if (getFragmentManager() != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .remove(placesFragment)
                            .commit();
                }
            }
        }

        //кнопка возврата на главный фрагмент
        final ImageButton backToMainButton = view.findViewById(R.id.back_to_main_button);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainFragment();
            }
        });
    }

    private void setThemeSwitch(@NonNull View view) {
        Switch themeSwitch = view.findViewById(R.id.dark_theme_switch);
        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null) return;
        themeSwitch.setChecked(mainActivity.isDarkTheme());
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainActivity.setDarkTheme(isChecked);
                mainActivity.recreate();
            }
        });
    }

    private void setWindCheckBox(@NonNull View view) {
        CheckBox windCheckBox = view.findViewById(R.id.wind_checkbox);
        windCheckBox.setChecked(isWindChecked());
        windCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setWindChecked(isChecked);
            }
        });
    }

    private void setHumidityCheckBox(@NonNull View view) {
        CheckBox humidityCheckBox = view.findViewById(R.id.humidity_checkbox);
        humidityCheckBox.setChecked(isHumidityChecked());
        humidityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setHumidityChecked(isChecked);
            }
        });
    }

    private void setPressureCheckBox(@NonNull View view) {
        CheckBox pressureCheckBox = view.findViewById(R.id.pressure_checkbox);
        pressureCheckBox.setChecked(isPressureChecked());
        pressureCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setPressureChecked(isChecked);
            }
        });
    }

    private void toMainFragment() {
        MainFragment fragment = MainFragment.newInstance();
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void setPlacesFragment() {
        placesFragment = PlacesFragment.newInstance(false);
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.next_fragment_container, placesFragment)
                    .commit();
        }
    }

    void setWindChecked(boolean checked) {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = getActivity().getPreferences(MODE_PRIVATE).edit();
            editor.putBoolean(WIND, checked);
            editor.apply();
        }
    }

    void setHumidityChecked(boolean checked) {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = getActivity().getPreferences(MODE_PRIVATE).edit();
            editor.putBoolean(HUMIDITY, checked);
            editor.apply();
        }
    }

    void setPressureChecked(boolean checked) {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = getActivity().getPreferences(MODE_PRIVATE).edit();
            editor.putBoolean(PRESSURE, checked);
            editor.apply();
        }
    }

    boolean isWindChecked() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getBoolean(WIND, false);
    }

    boolean isHumidityChecked() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getBoolean(HUMIDITY, false);
    }

    boolean isPressureChecked() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getBoolean(PRESSURE, false);
    }
}
