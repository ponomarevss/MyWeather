package com.ponomarevss.myweatherapp.model;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ponomarevss.myweatherapp.R;
import com.ponomarevss.myweatherapp.ui.home.HomeFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import static com.ponomarevss.myweatherapp.Constants.TAG;

public class WeatherRequest {
    private Clouds clouds;
    private Coord coord;
    private int dt;
    private Main main;
    private Sys sys;
    private int visibility;
    private Weather[] weather;
    private Wind wind;

//    public Clouds getClouds() {
//        return clouds;
//    }

//    public void setClouds(Clouds clouds) {
//        this.clouds = clouds;
//    }

    public Coord getCoord() {
        return coord;
    }

//    public void setCoord(Coord coord) {
//        this.coord = coord;
//    }

//    public int getDt() {
//        return dt;
//    }

//    public void setDt(int dt) {
//        this.dt = dt;
//    }

    public Main getMain() {
        return main;
    }

//    public void setMain(Main main) {
//        this.main = main;
//    }

    public Sys getSys() {
        return sys;
    }

//    public void setSys(Sys sys) {
//        this.sys = sys;
//    }

    public int getVisibility() {
        return visibility;
    }

//    public void setVisibility(int visibility) {
//        this.visibility = visibility;
//    }

    public Weather[] getWeather() {
        return weather;
    }

//    public void setWeather(Weather[] weather) {
//        this.weather = weather;
//    }

    public Wind getWind() {
        return wind;
    }

//    public void setWind(Wind wind) {
//        this.wind = wind;
//    }

    public void makeRequest(HomeFragment fragment, View view, String uri) {
        final View v = view;
        final HomeFragment f = fragment;
        f.showMessage(v, f.getString(R.string.loading));
        try {
            final URL url = new URL(uri);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        f.setWeatherRequest(gson.fromJson(result, WeatherRequest.class));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                f.hideMessage(v);
                                f.init(v);
                            }
                        });
                    } catch (final IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                f.hideMessage(v);
                                f.showMessage(v, f.getString(R.string.failed_to_get_data));
                            }
                        });
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }

    }

    @SuppressLint("NewApi")
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
