package com.example.dragonhack.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dragonhack.Constants;
import com.example.dragonhack.database.dao.SestavinaDao;
import com.example.dragonhack.database.entity.ProductDetails;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



@androidx.room.Database(entities = {ProductDetails.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {


    public abstract SestavinaDao recipeDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbWriteExec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile Database INSTANCE;

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, Constants.DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}