package com.example.wagba;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wagba.DaoClass.DaoClass;
import com.example.wagba.EntityClass.UserModel;

@Database(entities = {UserModel.class}, version = 1)
public abstract class DatabaseClass extends RoomDatabase {

    public abstract DaoClass getDao();

    private static DatabaseClass instance;


    static DatabaseClass getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DatabaseClass.class) {
                instance = Room.databaseBuilder(context, DatabaseClass.class, "DATABASE").allowMainThreadQueries().build();
            }
        }
        return instance;


    }
}