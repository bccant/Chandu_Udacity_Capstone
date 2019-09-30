package com.chandu.chandu_udacity_capstone.Widgets;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.chandu.chandu_udacity_capstone.Database.HikeDatabase;
import com.chandu.chandu_udacity_capstone.Database.HikeEntry;
import com.chandu.chandu_udacity_capstone.HikeActivity.MainActivity;
import com.chandu.chandu_udacity_capstone.HikeActivity.TrailOptionActivity;
import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.Utilities.AppExecutors;
import com.chandu.chandu_udacity_capstone.Utilities.SharedPreferencesUtil;
import com.chandu.chandu_udacity_capstone.hike.Hike;

import java.util.List;

public class TrailWidgetService extends IntentService {
    private static HikeDatabase rHDb = null;
    public static final String ACTION_FAV_TRAILS = "WIDGET_TRAILS";
    private boolean hikeDBEmpty = true;
    private static List<HikeEntry> fullHikeList = null;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TrailWidgetService(String name) {
        super(name);
    }

    public static void startSavedTrails(Context context) {
        Intent intent = new Intent(context, TrailWidgetService.class);
        intent.setAction(ACTION_FAV_TRAILS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            handleSavedTrails();
        }
    }

    private void handleSavedTrails() {
        if (rHDb == null) {
            rHDb = HikeDatabase.getsInstance(getApplicationContext());
        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                fullHikeList = extractHikesFromDB(getApplicationContext());
                handleRecipeDesc();
            }
        });
    }

    public static List<HikeEntry> extractHikesFromDB(Context context) {
        if (rHDb == null) {
            rHDb = HikeDatabase.getsInstance(context);
        }

        List<HikeEntry> hikeEntries = rHDb.hikeDAONL().loadAllHikesById();

        if (fullHikeList != null) {
            fullHikeList.clear();
        }

        return hikeEntries;
    }

    private void handleRecipeDesc() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        if (fullHikeList != null) {
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, TrailWidgetProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.trail_list_view);
            TrailWidgetProvider.updateTrailWidget(this, appWidgetManager, fullHikeList, appWidgetIds);
        } else {
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, TrailWidgetProvider.class));
            TrailWidgetProvider.updateTrailWidget(this, appWidgetManager, fullHikeList, appWidgetIds);
        }
    }

}
