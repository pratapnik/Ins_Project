package com.odroid.inspro;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TrendingMovie.class, NowPlayingMovie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}