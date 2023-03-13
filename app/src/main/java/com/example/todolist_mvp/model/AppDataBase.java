package com.example.todolist_mvp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ConcurrentModificationException;

@Database(version = 1 , exportSchema = false , entities = {Task.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase appDataBase;

    public static AppDataBase getAppDataBase(Context context){
        if (appDataBase==null)
            appDataBase= Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class , "dp_app")
                    .allowMainThreadQueries()
                    .build();

        return appDataBase;
    }

    public abstract TaskDao getTaskDao();
}
