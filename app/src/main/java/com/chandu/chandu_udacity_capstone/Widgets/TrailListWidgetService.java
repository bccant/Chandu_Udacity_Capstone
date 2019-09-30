package com.chandu.chandu_udacity_capstone.Widgets;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chandu.chandu_udacity_capstone.Database.HikeEntry;
import com.chandu.chandu_udacity_capstone.R;

import java.util.List;

public class TrailListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TrailListViewFactory(this.getApplicationContext());
    }
}

class TrailListViewFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    final String hDBHasValues = "trailsInDB";
    List<HikeEntry> hikeEntry = null;
    public TrailListViewFactory(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        hikeEntry = TrailWidgetService.extractHikesFromDB(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return hikeEntry.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.trail_list);
        String hikeName = hikeEntry.get(position).getHikeName();
        view.setTextViewText(R.id.trail_list, hikeName);

        Intent detailactivity = new Intent();
        detailactivity.putExtra(hDBHasValues, "true");

        view.setOnClickFillInIntent(R.id.trail_list, detailactivity);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
