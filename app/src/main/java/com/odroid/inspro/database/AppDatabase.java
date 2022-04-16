package com.odroid.inspro.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.odroid.inspro.entity.BaseMovie;

@Database(entities = {BaseMovie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}