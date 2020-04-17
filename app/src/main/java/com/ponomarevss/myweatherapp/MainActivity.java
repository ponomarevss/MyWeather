package com.ponomarevss.myweatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;

public class MainActivity extends AppCompatActivity {

    private Parcel parcel = new Parcel(SET_PLACE, INIT_INDEX,false, false, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) return;
        MainFragment fragment = MainFragment.newInstance(parcel);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

/*
        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск! ";
        }
        else {
            instanceState = "Повторный запуск! ";
        }
*/
    }

/*
    private void logInstanceState(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        Log.d("log", s);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logInstanceState("MainActivity - onStart()");
    }
    @Override
    protected void onResume() {
        super.onResume();
        logInstanceState("MainActivity - onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        logInstanceState("MainActivity - onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        logInstanceState("MainActivity - onStop()");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        logInstanceState("MainActivity - onRestart()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        logInstanceState("MainActivity - onDestroy()");
    }
*/
/*
    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putSerializable(INSTANT_STATE, currentParcel);
        super.onSaveInstanceState(saveInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(@Nullable Bundle saveInstanceState) {
        if (saveInstanceState != null){
            saveInstanceState.getSerializable(INSTANT_STATE);
            super.onRestoreInstanceState(saveInstanceState);
        }
    }
*/
}
