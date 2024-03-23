package com.example.tripmanager.infrastructure.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TripDao {
    @Query("SELECT * FROM tripentity")
    List<TripEntity> getAll();

    @Insert
    void insert(TripEntity user);

}
