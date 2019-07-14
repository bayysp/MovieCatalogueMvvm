package com.example.asus.subthreemvvm.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MovieModelDb.class},version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDAO movieDAO();
    private static AppDatabase appDatabase;

    public static AppDatabase initDatabase(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "favorite_database"
            ).allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
