package com.ponomarevss.myweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ponomarevss.myweatherapp.model.WeatherRequest;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.ponomarevss.myweatherapp.Constants.COORDINATES;
import static com.ponomarevss.myweatherapp.Constants.INDEX;
import static com.ponomarevss.myweatherapp.Constants.INIT_INDEX;
import static com.ponomarevss.myweatherapp.Constants.PLACE;
import static com.ponomarevss.myweatherapp.Constants.PRESSURE_AND_HUMIDITY;
import static com.ponomarevss.myweatherapp.Constants.SET_PLACE;
import static com.ponomarevss.myweatherapp.Constants.SUNRISE_AND_SUNSET;
import static com.ponomarevss.myweatherapp.Constants.TEMPERATURE_DETAILS;
import static com.ponomarevss.myweatherapp.Constants.VISIBILITY;
import static com.ponomarevss.myweatherapp.Constants.WEATHER_API_KEY;
import static com.ponomarevss.myweatherapp.Constants.WEATHER_ICON_PREFIX;
import static com.ponomarevss.myweatherapp.Constants.WEATHER_URL;
import static com.ponomarevss.myweatherapp.Constants.WIND_SPEED_AND_DIRECTION;

public class MainFragment extends Fragment {

    private WeatherRequest weatherRequest = new WeatherRequest();

    static MainFragment newInstance() {
        return new MainFragment();
    }

    public void setWeatherRequest(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         *
         * при создании главного фрагмента:
         *
         * если savedInstanceState == null, проверяем последнее обновление погоды по текущему месту
         * если savedInstanceState != null, проверяем соответствие места и последнее обновление
         * если надо обновить данные (сменилось место или прошло время), выполняем запрос на сервер
         *
         * после чего заполняем данными фрагмент
         *
         * */

        weatherRequest.makeRequest(this, view, String.format(WEATHER_URL + WEATHER_API_KEY, getCityId()));

        //тест массива координат получаем координаты
//        float lat = Float.parseFloat(getResources().getStringArray(R.array.cities_coord)[0].split("\\s")[0]);
//        float lon = Float.parseFloat(getResources().getStringArray(R.array.cities_coord)[0].split("\\s")[1]);
//        Snackbar.make(view, lat + "\n" + lon, Snackbar.LENGTH_INDEFINITE).show();

    }

    public void init(@NonNull View view) {
        setPlaceView(view);
        showCoordinatesView(view);
        showWeatherIconView(view);
        showTemperatureView(view);
        showWeatherDescriptionView(view);
        showTemperatureDetailsView(view);
        showPressureHumidityView(view);
        showVisibilityView(view);
        showWindView(view);
        showSunView(view);
        goToBrowserButton(view);
    }


    private void showWeatherIconView(View view) {
        ImageView weatherIcon = view.findViewById(R.id.weather_icon);
        String iconName = WEATHER_ICON_PREFIX + weatherRequest.getWeather()[0].getIcon();
        if (getActivity() != null) {
            weatherIcon.setImageResource(getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        }
    }

    private void showSunView(View view) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(SUNRISE_AND_SUNSET, false)) {
            LinearLayout sunLayout = view.findViewById(R.id.sun_layout);
            makeField(sunLayout, R.string.sunrise_field, String.valueOf(weatherRequest.getSys().getSunrise()), R.string.time_unit);
            makeField(sunLayout, R.string.sunset_field, String.valueOf(weatherRequest.getSys().getSunset()), R.string.time_unit);
        }
    }

    private void showWindView(View view) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(WIND_SPEED_AND_DIRECTION, false)) {
            LinearLayout windLayout = view.findViewById(R.id.wind_layout);
            makeField(windLayout, R.string.wind_speed_field, String.valueOf(weatherRequest.getWind().getSpeed()), R.string.wind_speed_unit);
            makeField(windLayout, R.string.wind_direction_field, String.valueOf(weatherRequest.getWind().getDeg()), R.string.wind_direction_unit);
        }
    }

    private void showVisibilityView(View view) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(VISIBILITY, false)) {
            LinearLayout visibilityLayout = view.findViewById(R.id.visibility_layout);
            makeField(visibilityLayout, R.string.visibility_field, String.valueOf(weatherRequest.getVisibility()), R.string.visibility_unit);
        }
    }

    private void showPressureHumidityView(View view) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(PRESSURE_AND_HUMIDITY, false)) {
            LinearLayout pressureHumidityLayout = view.findViewById(R.id.pressure_humidity_layout);
            makeField(pressureHumidityLayout, R.string.pressure_field, String.valueOf(weatherRequest.getMain().getPressure()), R.string.pressure_unit);
            makeField(pressureHumidityLayout, R.string.humidity_field, String.valueOf(weatherRequest.getMain().getHumidity()), R.string.humidity_unit);
        }
    }

    private void showTemperatureDetailsView(View view) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(TEMPERATURE_DETAILS, false)) {
            LinearLayout temperatureDetailsLayout = view.findViewById(R.id.temperature_details_layout);
            makeField(temperatureDetailsLayout, R.string.feels_like_field, String.valueOf(weatherRequest.getMain().getFeels_like()), R.string.temperature_unit);
            makeField(temperatureDetailsLayout, R.string.temp_max_field, String.valueOf(weatherRequest.getMain().getTemp_max()), R.string.temperature_unit);
            makeField(temperatureDetailsLayout, R.string.temp_min_field, String.valueOf(weatherRequest.getMain().getTemp_min()), R.string.temperature_unit);
        }
    }

    private void showTemperatureView(View view) {
            TextView temperatureView = view.findViewById(R.id.temperature_value);
            temperatureView.setText(String.valueOf(weatherRequest.getMain().getTemp()));
            TextView temperatureUnitView = view.findViewById(R.id.temperature_unit);
            temperatureUnitView.setText(R.string.temperature_unit);
    }

    private void showWeatherDescriptionView(View view) {
        TextView weatherDescriptionView = view.findViewById(R.id.weather_description);
        weatherDescriptionView.setText(weatherRequest.getWeather()[0].getDescription());
    }

    private void showCoordinatesView(View view) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(COORDINATES, false)) {
            LinearLayout coordinatesLayout = view.findViewById(R.id.coordinates_layout);
            makeField(coordinatesLayout, R.string.latitude_field, String.valueOf(weatherRequest.getCoord().getLat()), R.string.coordinates_unit);
            makeField(coordinatesLayout, R.string.longitude_field, String.valueOf(weatherRequest.getCoord().getLon()), R.string.coordinates_unit);
        }
    }

    private void makeField(LinearLayout layout, int name, String value, int unit) {
        View view = getLayoutInflater().inflate(R.layout.detail_layout, layout, false);
        ((TextView) view.findViewById(R.id.detail_name))
                .setText(getResources().getString(name)); //имя
        ((TextView) view.findViewById(R.id.detail_value))
                .setText(value); //значение
        ((TextView) view.findViewById(R.id.detail_unit))
                .setText(getResources().getString(unit)); //единица измерения
        //TODO: прописать подстановку значений
        layout.addView(view);
    }

    private void goToBrowserButton(@NonNull View view) {
        TextView moreInfo = view.findViewById(R.id.more_info);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] city = getResources().getStringArray(R.array.cities);
                String[] cityUrl = getResources().getStringArray(R.array.cities_id);
                Map<String, String> cityHm= new HashMap<>();
                for (int i = 0; i < city.length; i++) {
                    cityHm.put(city[i], cityUrl[i]);
                }
                assert getActivity() != null;
                String url = getResources().getString(R.string.url) + cityHm.get(getPlace());
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Context context = getContext();
                if (context == null) return;
                ActivityInfo activityInfo = intent.resolveActivityInfo(context.getPackageManager(), intent.getFlags());
                if (activityInfo != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private String getCityId() {
        assert getActivity() != null;
        return getResources().getStringArray(R.array.cities_id)[getActivity().getPreferences(MODE_PRIVATE).getInt(INDEX, INIT_INDEX)];
    }

    private String getPlace() {
        assert getActivity() != null;
        return getActivity().getPreferences(MODE_PRIVATE).getString(PLACE, SET_PLACE);
    }
    private void setPlaceView(View view) {
        TextView placeTextView = view.findViewById(R.id.place);
        assert getActivity() != null;
        placeTextView.setText(getPlace());
    }

    public void showMessage(View view, String s) {
        TextView messageView = view.findViewById(R.id.message);
        messageView.setVisibility(View.VISIBLE);
        messageView.setText(s);
    }

    public void hideMessage(View view) {
        TextView messageView = view.findViewById(R.id.message);
        messageView.setVisibility(View.GONE);
    }
}
