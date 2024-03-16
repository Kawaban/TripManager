package com.example.tripmanager.flixbusactivity.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {CityEntity.class}, version = 1, exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
