package com.chandu.chandu_udacity_capstone.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HikeEntry.class}, version = 1, exportSchema = false)
public abstract class HikeDatabase extends RoomDatabase {
    private static final String LOG_TAG = HikeDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "faveHikeList";
    private static HikeDatabase sInstance;

    public static HikeDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new DB instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        HikeDatabase.class, HikeDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the DB instance");
        return sInstance;
    }

    public abstract HikeDAO hikeDAO();
    public abstract HikeDAONL hikeDAONL();
}
