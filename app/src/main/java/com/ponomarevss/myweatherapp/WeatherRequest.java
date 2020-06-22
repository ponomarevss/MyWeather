package com.ponomarevss.myweatherapp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.ponomarevss.myweatherapp.ui.home.HomeFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import static com.ponomarevss.myweatherapp.Constants.TAG;

public class WeatherRequest {

//    private WeatherModel weatherModel;
    private Fragment fragment;
    private View view;
    private String uri;
    private RequestHandler requestHandler;

    public WeatherRequest(Fragment fragment, View view, String uri) {
        this.fragment = fragment;
        this.view = view;
        this.uri = uri;
    }

//    public WeatherRequest(WeatherModel weatherModel) {
//        this.weatherModel = weatherModel;
//    }

//    public WeatherModel getWeatherModel() {
//        return weatherModel;
//    }

//    public void setWeatherModel(WeatherModel weatherModel) {
//        this.weatherModel = weatherModel;
//    }

    public void makeRequest(/*final Fragment fragment, final View view, String uri*/) {
        ((MainActivity) Objects.requireNonNull(fragment.getActivity())).showAlertMessage(fragment.getString(R.string.loading));
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
                        // получили стрингу от сервера и привлекаем requestHandler для обработки результата

                        // requestHandler получает строку обрабатывает ее и передает во фрагмент WeatherModel
                        requestHandler = new RequestHandler(result);
//                        ((HomeFragment) fragment).setWeatherModel(requestHandler.getWeatherModel()); //todo
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ((MainActivity) Objects.requireNonNull(fragment.getActivity())).hideAlertMessage();
                                ((HomeFragment) fragment).init(view, requestHandler);
                            }
                        });
                    } catch (final IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ((MainActivity) Objects.requireNonNull(fragment.getActivity())).hideAlertMessage();
                                ((MainActivity) Objects.requireNonNull(fragment.getActivity())).showAlertMessage(fragment.getString(R.string.failed_to_get_data));
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
