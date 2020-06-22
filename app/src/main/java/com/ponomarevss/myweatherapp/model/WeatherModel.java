package com.ponomarevss.myweatherapp.model;

public class WeatherModel {
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

    public Coord getCoord() {
        return coord;
    }

//    public int getDt() {
//        return dt;
//    }

    public Main getMain() {
        return main;
    }

    public Sys getSys() {
        return sys;
    }

    public int getVisibility() {
        return visibility;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

}
