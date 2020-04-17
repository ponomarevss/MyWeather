package com.ponomarevss.myweatherapp;

import android.os.Build;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Parcel implements Parcelable {

    private String place;
    private int index;
    private boolean windChecked;
    private boolean humidityChecked;
    private boolean pressureChecked;

    public Parcel(String place, int index, boolean windChecked, boolean humidityChecked, boolean pressureChecked) {
        this.place = place;
        this.index = index;
        this.windChecked = windChecked;
        this.humidityChecked = humidityChecked;
        this.pressureChecked = pressureChecked;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isWindChecked() {
        return windChecked;
    }

    public void setWindChecked(boolean windChecked) {
        this.windChecked = windChecked;
    }

    public boolean isHumidityChecked() {
        return humidityChecked;
    }

    public void setHumidityChecked(boolean humidityChecked) {
        this.humidityChecked = humidityChecked;
    }

    public boolean isPressureChecked() {
        return pressureChecked;
    }

    public void setPressureChecked(boolean pressureChecked) {
        this.pressureChecked = pressureChecked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(place);
        dest.writeInt(index);
        dest.writeBoolean(windChecked);
        dest.writeBoolean(humidityChecked);
        dest.writeBoolean(pressureChecked);
    }

    public static final Creator<Parcel> CREATOR = new Creator<Parcel>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Parcel createFromParcel(android.os.Parcel source) {
            String place = source.readString();
            int index = source.readInt();
            boolean windChecked = source.readBoolean();
            boolean humidityChecked = source.readBoolean();
            boolean pressureChecked = source.readBoolean();
            return new Parcel(place, index, windChecked, humidityChecked, pressureChecked);
        }

        @Override
        public Parcel[] newArray(int size) {
            return new Parcel[size];
        }
    };
}

