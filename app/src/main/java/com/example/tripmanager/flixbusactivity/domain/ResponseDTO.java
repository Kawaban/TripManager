package com.example.tripmanager.flixbusactivity.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ResponseDTO implements Parcelable {
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String price;
    private String duration;
    private String link;
    private int changeovers;

    public ResponseDTO(String origin, String destination, String departureTime, String arrivalTime, String price, String duration, String link, int changeovers) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.duration = duration;
        this.link = link;
        this.changeovers = changeovers;
    }

    public ResponseDTO(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(origin);
        dest.writeString(destination);
        dest.writeString(departureTime);
        dest.writeString(arrivalTime);
        dest.writeString(price);
        dest.writeString(duration);
        dest.writeString(link);
        dest.writeInt(changeovers);

    }

    private void readFromParcel(Parcel in) {
        origin = in.readString();
        destination = in.readString();
        departureTime = in.readString();
        arrivalTime = in.readString();
        price = in.readString();
        duration = in.readString();
        link = in.readString();
        changeovers = in.readInt();
    }
}
