package com.example.tripmanager.infrastructure.database;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TripEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String location;
    public String expenses;
    public String startDate;
    public String endDate;
    public float rating;
    @TypeConverters(Converters.class)
    public ArrayList<Uri> images;

}
