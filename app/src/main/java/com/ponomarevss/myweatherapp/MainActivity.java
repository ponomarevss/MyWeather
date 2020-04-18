package com.ponomarevss.myweatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;
import static com.ponomarevss.myweatherapp.Constants.MAIN_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;

public class MainActivity extends AppCompatActivity {

    private Parcel parcel = new Parcel(SET_PLACE, INIT_INDEX,false, false, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) return;
        MainFragment fragment = MainFragment.newInstance(parcel, MAIN_FRAGMENT);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

}
