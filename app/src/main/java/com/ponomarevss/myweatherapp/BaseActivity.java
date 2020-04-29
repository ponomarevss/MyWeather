package com.ponomarevss.myweatherapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.ponomarevss.myweatherapp.Constants.IS_DARK_THEME;
import static com.ponomarevss.myweatherapp.Constants.NAMED_SHARED_PREFERENCE;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    boolean isDarkTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(NAMED_SHARED_PREFERENCE, MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_DARK_THEME, true);
    }

    void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPreferences = getSharedPreferences(NAMED_SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_DARK_THEME, isDarkTheme);
        editor.apply();
    }
}
