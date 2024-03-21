package com.example.tripmanager.locationactivity.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ResponseDTO implements Parcelable {
    private String latitude;
    private String longitude;
    private String name;
    private String info;
    private String rating;
    private String type;
    public ResponseDTO(String latitude, String longitude, String name, String info, String rating, String type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.info = info;
        this.rating = rating;
        this.type = type;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(name);
        dest.writeString(info);
        dest.writeString(rating);
        dest.writeString(type);
    }

    public ResponseDTO(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
        name = in.readString();
        info = in.readString();
        rating = in.readString();
        type = in.readString();
    }

    public static final Parcelable.Creator<ResponseDTO> CREATOR = new Parcelable.Creator<ResponseDTO>() {
        public ResponseDTO createFromParcel(Parcel in) {
            return new ResponseDTO(in);
        }

        public ResponseDTO[] newArray(int size) {
            return new ResponseDTO[size];
        }
    };


    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
