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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import static com.ponomarevss.myweatherapp.Constants.CHOSEN_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.MAIN_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.PARCEL;
import static com.ponomarevss.myweatherapp.Constants.PLACE_FRAGMENT;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;

public class PlacesFragment extends Fragment {

    private Parcel parcel;
    private SettingsFragment settingsFragment;
    private String chosenFragment;
    private TextView placeTextView;

    private boolean isLandscape;

    static PlacesFragment newInstance(Parcel parcel, String string) {
        PlacesFragment fragment = new PlacesFragment();
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
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (chosenFragment.equals(PLACE_FRAGMENT)) {
            replaceSettingsFragment();
            if(!isLandscape) {
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(settingsFragment);
                    fragmentTransaction.commit();
                }
            }
        }

        //поле выбранного места
        placeTextView = view.findViewById(R.id.place_set);
        placeTextView.setText(parcel.getPlace());

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
                parcel.setPlace(place);
                parcel.setIndex(index);
                placeTextView.setText(place);

                if(!isLandscape) {
                    chosenFragment = MAIN_FRAGMENT;
                    replaceMainFragment();
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
                parcel.setPlace(placeTextView.getText().toString());
                chosenFragment = MAIN_FRAGMENT;
                replaceMainFragment();
            }
        });
    }

    private void replaceMainFragment() {
        MainFragment fragment = MainFragment.newInstance(parcel, chosenFragment);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void replaceSettingsFragment() {
        settingsFragment = SettingsFragment.newInstance(parcel, chosenFragment);
        if (getFragmentManager() == null) return;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, settingsFragment);
        fragmentTransaction.commit();
    }
}
