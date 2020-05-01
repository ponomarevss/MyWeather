package com.ponomarevss.myweatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.HUMIDITY;
import static com.ponomarevss.myweatherapp.Constants.PRESSURE;
import static com.ponomarevss.myweatherapp.Constants.WIND;


public class SettingsFragment extends Fragment {

    static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void setWindChecked(boolean checked) {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = getActivity().getPreferences(MODE_PRIVATE).edit();
            editor.putBoolean(WIND, checked);
            editor.apply();
        }
    }

    private void setHumidityChecked(boolean checked) {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = getActivity().getPreferences(MODE_PRIVATE).edit();
            editor.putBoolean(HUMIDITY, checked);
            editor.apply();
        }
    }

    private void setPressureChecked(boolean checked) {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = getActivity().getPreferences(MODE_PRIVATE).edit();
            editor.putBoolean(PRESSURE, checked);
            editor.apply();
        }
    }

    private boolean isWindChecked() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getBoolean(WIND, false);
    }

    private boolean isHumidityChecked() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getBoolean(HUMIDITY, false);
    }

    private boolean isPressureChecked() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getBoolean(PRESSURE, false);
    }
}
