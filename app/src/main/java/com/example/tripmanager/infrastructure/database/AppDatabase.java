package com.example.tripmanager.infrastructure.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {CityEntity.class, TripEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
    public abstract CityDao cityDao();


}
