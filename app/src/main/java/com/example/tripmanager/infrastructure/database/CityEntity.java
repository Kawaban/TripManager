package com.example.tripmanager.infrastructure.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "city_name")
    public String city_name;
    @ColumnInfo(name = "flixbus_id")
    public String flixbus_id;
}
