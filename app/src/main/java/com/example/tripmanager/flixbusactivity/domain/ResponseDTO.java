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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getChangeovers() {
        return changeovers;
    }

    public void setChangeovers(int changeovers) {
        this.changeovers = changeovers;
    }

    public static final Parcelable.Creator<ResponseDTO> CREATOR = new Parcelable.Creator<ResponseDTO>() {
        public ResponseDTO createFromParcel(Parcel in) {
            return new ResponseDTO(in);
        }

        public ResponseDTO[] newArray(int size) {
            return new ResponseDTO[size];
        }
    };


}
