package com.ponomarevss.myweatherapp.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ponomarevss.myweatherapp.R;
import com.ponomarevss.myweatherapp.RequestHandler;
import com.ponomarevss.myweatherapp.WeatherRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
import static com.ponomarevss.myweatherapp.Constants.WEATHER_URL;
import static com.ponomarevss.myweatherapp.Constants.WIND_SPEED_AND_DIRECTION;

public class HomeFragment extends Fragment {

//    private WeatherModel weatherModel/* = new WeatherModel()*/;
//    private RequestHandler requestHandler;

//    public void setWeatherModel(WeatherModel weatherModel) {
//        this.weatherModel = weatherModel;
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackgroundView(view);
        if (getCityId() != null) {
            new WeatherRequest(this, view, String.format(WEATHER_URL + WEATHER_API_KEY, getCityId()))
                    .makeRequest();
//            RequestHandler requestHandler = new RequestHandler(this, view, String.format(WEATHER_URL + WEATHER_API_KEY, getCityId()));
//            weatherModel.makeRequest(this, view, String.format(WEATHER_URL + WEATHER_API_KEY, getCityId()));
        }
    }

    public void init(@NonNull View view, RequestHandler requestHandler) {
        setPlaceView(view);
        showCoordinatesView(view, requestHandler);
        showWeatherIconView(view, requestHandler);
        showTemperatureView(view, requestHandler);
        showWeatherDescriptionView(view, requestHandler);
        showTemperatureDetailsView(view, requestHandler);
        showPressureHumidityView(view, requestHandler);
        showVisibilityView(view, requestHandler);
        showWindView(view, requestHandler);
        showSunView(view, requestHandler);
        goToBrowserButton(view);
    }

    private void showWeatherIconView(View view, RequestHandler requestHandler) {
        ImageView weatherIcon = view.findViewById(R.id.weather_icon);
//        String iconName = WEATHER_ICON_PREFIX + weatherModel.getWeather()[0].getIcon();
        if (getActivity() != null) {
            weatherIcon.setImageResource(getResources().getIdentifier(requestHandler.getIconName(),
                    "drawable",
                    getActivity().getPackageName()));
        }
    }

    private void showSunView(View view, RequestHandler requestHandler) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(SUNRISE_AND_SUNSET, false)) {
            LinearLayout sunLayout = view.findViewById(R.id.sun_layout);
            makeField(sunLayout, R.string.sunrise_field, requestHandler.getSunrise(), R.string.time_unit);
            makeField(sunLayout, R.string.sunset_field, requestHandler.getSunset(), R.string.time_unit);
        }
    }

    private void showWindView(View view, RequestHandler requestHandler) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(WIND_SPEED_AND_DIRECTION, false)) {
            LinearLayout windLayout = view.findViewById(R.id.wind_layout);
            makeField(windLayout, R.string.wind_speed_field, requestHandler.getWindSpeed(), R.string.wind_speed_unit);
            makeField(windLayout, R.string.wind_direction_field, requestHandler.getWindDeg(), R.string.wind_direction_unit);
        }
    }

    private void showVisibilityView(View view, RequestHandler requestHandler) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(VISIBILITY, false)) {
            LinearLayout visibilityLayout = view.findViewById(R.id.visibility_layout);
            makeField(visibilityLayout, R.string.visibility_field, requestHandler.getVisibility(), R.string.visibility_unit);
        }
    }

    private void showPressureHumidityView(View view, RequestHandler requestHandler) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(PRESSURE_AND_HUMIDITY, false)) {
            LinearLayout pressureHumidityLayout = view.findViewById(R.id.pressure_humidity_layout);
            makeField(pressureHumidityLayout, R.string.pressure_field, requestHandler.getPressure(), R.string.pressure_unit);
            makeField(pressureHumidityLayout, R.string.humidity_field, requestHandler.getHumidity(), R.string.humidity_unit);
        }
    }

    private void showTemperatureDetailsView(View view, RequestHandler requestHandler) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(TEMPERATURE_DETAILS, false)) {
            LinearLayout temperatureDetailsLayout = view.findViewById(R.id.temperature_details_layout);
            makeField(temperatureDetailsLayout, R.string.feels_like_field, requestHandler.getTempFeels_like(), R.string.temperature_unit);
            makeField(temperatureDetailsLayout, R.string.temp_max_field, requestHandler.getTemp_max(), R.string.temperature_unit);
            makeField(temperatureDetailsLayout, R.string.temp_min_field, requestHandler.getTemp_min(), R.string.temperature_unit);
        }
    }

    private void showTemperatureView(View view, RequestHandler requestHandler) {
        TextView temperatureView = view.findViewById(R.id.temperature_value);
        temperatureView.setText(requestHandler.getTemp());
        TextView temperatureUnitView = view.findViewById(R.id.temperature_unit);
        temperatureUnitView.setText(R.string.temperature_unit);
    }

    private void showWeatherDescriptionView(View view, RequestHandler requestHandler) {
        TextView weatherDescriptionView = view.findViewById(R.id.weather_description);
        weatherDescriptionView.setText(requestHandler.getWeatherDescription());
    }

    private void showCoordinatesView(View view, RequestHandler requestHandler) {
        if (getActivity() != null && getActivity().getPreferences(MODE_PRIVATE).getBoolean(COORDINATES, false)) {
            LinearLayout coordinatesLayout = view.findViewById(R.id.coordinates_layout);
            makeField(coordinatesLayout, R.string.latitude_field, requestHandler.getCoordLat(), R.string.coordinates_unit);
            makeField(coordinatesLayout, R.string.longitude_field, requestHandler.getCoordLon(), R.string.coordinates_unit);
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
        layout.addView(view);
    }

    private void goToBrowserButton(@NonNull View view) {
        TextView moreInfo = view.findViewById(R.id.more_info);
        moreInfo.setVisibility(View.VISIBLE);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setMessage(R.string.lets_go_to_web)
                        .setCancelable(true)
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //не делать ничего
                            }
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                        })
                        .create()
                        .show();
            }
        });
    }

    private String getCityId() {
        int index = Objects.requireNonNull(getActivity())
                .getPreferences(MODE_PRIVATE)
                .getInt(INDEX, INIT_INDEX);
        return index != -1 ? getResources().getStringArray(R.array.cities_id)[index] : null;
    }
    private String getPlace() {
        return Objects.requireNonNull(getActivity())
                .getPreferences(MODE_PRIVATE)
                .getString(PLACE, SET_PLACE);
    }

    private void setPlaceView(View view) {
        TextView placeTextView = view.findViewById(R.id.place);
        placeTextView.setText(getPlace());
    }

    private int getBackgroundIndex() {
        return Objects.requireNonNull(getActivity())
                .getPreferences(MODE_PRIVATE)
                .getInt(INDEX, INIT_INDEX);
    }

    private void setBackgroundView(View view) {
        ImageView background = view.findViewById(R.id.background);
        TypedArray images = getResources().obtainTypedArray(R.array.city_images);
        if (getBackgroundIndex() == INIT_INDEX) {
            background.setImageResource(R.drawable.background_default);
        } else {
            background.setImageResource(images.getResourceId(getBackgroundIndex(), -1));
        }
        background.setVisibility(View.VISIBLE);
        images.recycle();
    }
}
