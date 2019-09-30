package com.chandu.chandu_udacity_capstone.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.chandu.chandu_udacity_capstone.Database.HikeEntry;
import com.chandu.chandu_udacity_capstone.HikeActivity.MainActivity;
import com.chandu.chandu_udacity_capstone.HikeActivity.TrailOptionActivity;
import com.chandu.chandu_udacity_capstone.R;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class TrailWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trail_widget_provider);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.appwidget_ingredient, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                List<HikeEntry> hikeEntry, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trail_widget_provider);

        if (hikeEntry == null || hikeEntry.size() == 0) {
            views = getEmptyView(context);
        } else {
            views = getHikesListView(context);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateTrailWidget(Context context, AppWidgetManager appWidgetManager,
                                         List<HikeEntry> hikeEntry, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, hikeEntry, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static RemoteViews getEmptyView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.trail_widget_provider);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.appwidget_ingredient, pendingIntent);
        views.setOnClickPendingIntent(R.id.appwidget_ingredient, pendingIntent);

        return views;
    }

    public static RemoteViews getHikesListView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trail_widget_list);
        views.setTextViewText(R.id.hike_title, context.getResources().getString(R.string.appwidget_text));

        Intent intent = new Intent(context, TrailListWidgetService.class);
        views.setRemoteAdapter(R.id.trail_list_view, intent);

        Intent detailactivity = new Intent(context, TrailOptionActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailactivity,
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.trail_list_view, pendingIntent);
        views.setEmptyView(R.id.trail_list_view, R.id.widget_empty);

        return views;
    }
}

