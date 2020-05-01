package com.ponomarevss.myweatherapp;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.INDEX;
import static com.ponomarevss.myweatherapp.Constants.PLACE;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;

public class PlacesFragment extends Fragment {

    private TextView placeTextView;

    static PlacesFragment newInstance() {
        return new PlacesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //поле выбранного места
        setPlaceTextView(view);

        //создаем список городов
        final String[] cities = getResources().getStringArray(R.array.cities);
        final TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getActivity() == null) return;
        RecyclerView citiesRecyclerView = getActivity().findViewById(R.id.cities_recycler_view);
        citiesRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        citiesRecyclerView.setLayoutManager(layoutManager);

        CitiesRecyclerAdapter adapter = new CitiesRecyclerAdapter(cities, images);

        //передаем интерфейс ClickListener'а
        adapter.setClickListener(new CitiesRecyclerAdapter.CitiesRecyclerClickListener() {
            @Override
            public void OnItemClick(String place, int index) {
                savePlace(place);
                saveIndex(index);
                placeTextView.setText(place);
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).setBackgroundView();
                }
            }
        });
        citiesRecyclerView.setAdapter(adapter);

        //кнопка определения места
        final MaterialButton locate = view.findViewById(R.id.locate_button);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: получить данные геолокации и установить в текущее место
                placeTextView.setText(SET_PLACE);
                Snackbar.make(view, "Определяем место", Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Определяем место", Toast.LENGTH_SHORT).show();
            }
        });

        //принять место
        final MaterialButton commitButton = view.findViewById(R.id.commit_button);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlace(placeTextView.getText().toString());
            }
        });

        //поле ввода текста
        final TextInputEditText textInputEditText = view.findViewById(R.id.input_place);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //что делать здесь? обновлять поле заданного места?
                //в качестве заглушки ставим snackBar
                Snackbar.make(view, Objects.requireNonNull(textInputEditText.getText()).toString(), Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void setPlaceTextView(@NonNull View view) {
        placeTextView = view.findViewById(R.id.place_set);
        placeTextView.setText(getPlace());
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
