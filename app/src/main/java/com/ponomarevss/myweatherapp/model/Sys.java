package com.ponomarevss.myweatherapp.model;

public class Sys {
    private int id;
    private long sunrise;
    private long sunset;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
