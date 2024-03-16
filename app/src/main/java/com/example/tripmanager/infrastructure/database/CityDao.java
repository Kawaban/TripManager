package com.example.tripmanager.infrastructure.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {
    @Query("SELECT * FROM cityentity")
    List<CityEntity> getAll();

    @Query("SELECT * FROM cityentity WHERE city_name LIKE :name LIMIT 1")
    CityEntity findByName(String name);

    @Insert
    void insertAll(CityEntity... users);

    @Delete
    void delete(CityEntity user);
}
