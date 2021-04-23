package com.example.desafiodatamob.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase dataBase;
    private static String DATABASE_NAME = "desafioDatamob";
    public synchronized static AppDataBase getInstance(Context context){
        if(dataBase == null){
            dataBase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBase;
    }

    public abstract PersonDAO personDAO();
}
