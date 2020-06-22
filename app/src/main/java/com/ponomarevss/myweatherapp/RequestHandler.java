package com.ponomarevss.myweatherapp;

import com.google.gson.Gson;
import com.ponomarevss.myweatherapp.model.WeatherModel;

import static com.ponomarevss.myweatherapp.Constants.WEATHER_ICON_PREFIX;

public class RequestHandler {

    WeatherModel weatherModel;
//    String result;

    public RequestHandler(String result) {
//        this.result = result;
        weatherModel = new Gson().fromJson(result, WeatherModel.class);
    }

//    public WeatherModel getWeatherModel() {
//        Gson gson = new Gson();
//        weatherModel = gson.fromJson(result, WeatherModel.class);
//        return weatherModel;
//    }

    public String getIconName() {
        return WEATHER_ICON_PREFIX + weatherModel.getWeather()[0].getIcon();
    }

    public String getCoordLat() {
        return String.valueOf(weatherModel.getCoord().getLat());
    }

    public String getCoordLon() {
        return String.valueOf(weatherModel.getCoord().getLon());
    }

    public String getTemp() {
        return String.valueOf(weatherModel.getMain().getTemp());
    }

    public String getWeatherDescription() {
        return weatherModel.getWeather()[0].getDescription();
    }

    public String getTempFeels_like() {
        return String.valueOf(weatherModel.getMain().getFeels_like());
    }
    public String getTemp_max() {
        return String.valueOf(weatherModel.getMain().getTemp_max());
    }
    public String getTemp_min() {
        return String.valueOf(weatherModel.getMain().getTemp_min());
    }

    public String getPressure() {
        return String.valueOf(weatherModel.getMain().getPressure());
    }
    public String getHumidity() {
        return String.valueOf(weatherModel.getMain().getHumidity());
    }

    public String getSunrise() {
        return String.valueOf(weatherModel.getSys().getSunrise());
    }
    public String getSunset() {
        return String.valueOf(weatherModel.getSys().getSunset());
    }

    public String getVisibility() {
        return String.valueOf(weatherModel.getVisibility());
    }

    public String getWindSpeed() {
        return String.valueOf(weatherModel.getWind().getSpeed());
    }
    public String getWindDeg() {
        return String.valueOf(weatherModel.getWind().getDeg());
    }
}
