package com.ponomarevss.myweatherapp;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.INDEX;
import static com.ponomarevss.myweatherapp.Constants.PLACE;
import static com.ponomarevss.myweatherapp.Constants.PLACE_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;

public class PlacesFragment extends Fragment {

    private SettingsFragment settingsFragment;
    private TextView placeTextView;
    private boolean isPrimal;
    private boolean isLandscape;

    static PlacesFragment newInstance(Boolean isPrimal) {
        PlacesFragment fragment = new PlacesFragment();
        Bundle args = new Bundle();
        args.putBoolean(PLACE_FRAGMENT, isPrimal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isPrimal = getArguments().getBoolean(PLACE_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        //создание фрагмента настроек в ландшафтной ориентации
        if (isPrimal) {
            setSettingsFragment();
            if(!isLandscape) {
                if(getFragmentManager() != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .remove(settingsFragment)
                            .commit();
                }
            }
        }

        //поле выбранного места
        setPlaceTextView(view);

        //создаем список городов
        final String[] cities = getResources().getStringArray(R.array.cities);
        final TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getActivity() == null) return;
        RecyclerView citiesRecyclerView = getActivity().findViewById(R.id.cities_recycler_view);
        citiesRecyclerView.setHasFixedSize(true);
        CitiesRecyclerAdapter adapter = new CitiesRecyclerAdapter(cities, images);

        //передаем интерфейс ClickListener'а
        adapter.setClickListener(new CitiesRecyclerAdapter.CitiesRecyclerClickListener() {
            @Override
            public void OnItemClick(String place, int index) {
                savePlace(place);
                saveIndex(index);
                placeTextView.setText(place);

                if(!isLandscape) {
                    toMainFragment();
                }
            }
        });
        citiesRecyclerView.setAdapter(adapter);

        //кнопка определения места
        final Button locate = view.findViewById(R.id.locate_button);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeTextView.setText(SET_PLACE);
                Toast.makeText(getContext(), "Определяем место", Toast.LENGTH_SHORT).show();
            }
        });

        //принять место
        final Button commitButton = view.findViewById(R.id.commit_button);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlace(placeTextView.getText().toString());
                toMainFragment();
            }
        });
    }

    private void setPlaceTextView(@NonNull View view) {
        placeTextView = view.findViewById(R.id.place_set);
        placeTextView.setText(getPlace());
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

    private void setSettingsFragment() {
        settingsFragment = SettingsFragment.newInstance(false);
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, settingsFragment)
                    .commit();
        }
    }

    private void savePlace(String place) {
        if (getActivity() != null) {
            getActivity().getPreferences(MODE_PRIVATE).edit().putString(PLACE, place).apply();
        }
    }

    private String getPlace() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getString(PLACE, SET_PLACE);
    }

    private void saveIndex(int index) {
        if (getActivity() != null) {
            getActivity().getPreferences(MODE_PRIVATE).edit().putInt(INDEX, index).apply();
        }
    }
}
