package com.ponomarevss.myweatherapp.model;

public class WeatherRequest {
    private Clouds clouds;
    private Coord coord;
    private Main main;
    private Sys sys;
    private Visibility visibility;
    private Weather[] weathers;
    private Wind wind;

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Weather[] getWeathers() {
        return weathers;
    }

    public void setWeathers(Weather[] weathers) {
        this.weathers = weathers;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
