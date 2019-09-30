package com.chandu.chandu_udacity_capstone.HikeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chandu.chandu_udacity_capstone.BuildConfig;
import com.chandu.chandu_udacity_capstone.Database.HikeDatabase;
import com.chandu.chandu_udacity_capstone.Database.HikeEntry;
import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.Utilities.HikeJasonUtils;
import com.chandu.chandu_udacity_capstone.Utilities.NetworkUtils;
import com.chandu.chandu_udacity_capstone.hike.Hike;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.maps.model.LatLng;

import android.content.Intent;
import android.graphics.Movie;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrailOptionActivity extends AppCompatActivity implements TrailOptionAdapter.TrailOptionAdapterOnClickHandler ,
        LoaderManager.LoaderCallbacks<List<Hike>> {
    private LatLng p1 = null;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private TrailOptionAdapter trailOptionAdapter;
    private LinearLayout trailHeaders;
    private static final int TRAIL_OPTIONS_LOADER = 22;
    private static final String SEARCH_QUERY_URL_EXTRA = "query";
    final String hikeURL = "https://www.hikingproject.com/data/get-trails?";
    final String hikeAPIKey = "key=" + BuildConfig.HikepiKey;
    final String hikeDistance = "&maxDistance=100&";
    final String hikeSort = "&sort=distance&";
    final String trailLat = "trailLat";
    final String trailLong = "trailLong";
    final String hikeObject = "hikeObject";
    final String hDBHasValues = "trailsInDB";
    String homeLat = "";
    String homeLong = "";
    String sortType = "distance";
    private HikeDatabase mHDb;
    private List<Hike> mHikeDetails = null;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private HikeDatabase mDB;
    private int optionID = R.id.sort_distance;
    private static final String LOG_TAG = TrailOptionActivity.class.getSimpleName();
    Parcelable savedRecyclerLayoutState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_option);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true);

        trailOptionAdapter = new TrailOptionAdapter(this);
        mRecyclerView.setAdapter(trailOptionAdapter);
        //trailHeaders = (LinearLayout) findViewById(R.id.trail_headers);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mDB = HikeDatabase.getsInstance(getApplicationContext());

        if (savedInstanceState != null) {
            optionID = savedInstanceState.getInt("SORT");
            Log.d("OnCreate", "SAVED INSTANCE is not NULL " + optionID);
        } else {
            Log.d("OnCreate", "SAVED INSTANCE is NULL");
        }

        if (savedInstanceState != null) {
            optionID = savedInstanceState.getInt("SORT");
            Log.d("OnCreate", "SAVED INSTANCE is not NULL " + optionID);
        } else {
            Log.d("OnCreate", "SAVED INSTANCE is NULL");
        }

        loadTrails(optionID);
    }

    private void loadTrails(final int optionValues) {
        int dbSort = 1;
        String trailURL = null;
        Intent intent = getIntent();
        String latLong = "";
        Log.d("loadTrails", "Option of loadTrails started");
        showTrailOptionDataView();

        //if ((intent != null) && intent.hasExtra(trailLat) && intent.hasExtra(trailLong)) {
        if (intent != null) {
            if (intent.hasExtra(trailLat) && intent.hasExtra(trailLong)) {
                homeLat = (String) intent.getStringExtra(trailLat);
                homeLong = (String) intent.getStringExtra(trailLong);

                latLong = "lat=" + homeLat + "&" + "lon=" + homeLong;


                switch (optionValues) {
                    case R.id.sort_difficulty:
                        sortType = "difficulty";
                        trailURL = hikeURL + latLong + hikeDistance + hikeAPIKey;
                        break;
                    case R.id.sort_length:
                        sortType = "length";
                        trailURL = hikeURL + latLong + hikeDistance + hikeAPIKey;
                        break;
                    case R.id.sort_distance:
                    default:
                        sortType = "distance";
                        trailURL = hikeURL + latLong + hikeDistance + hikeSort + hikeAPIKey;
                        Log.d("loadMovies", "calling loadFavMovieData ");
                        break;
                }

                Bundle queryBundle = new Bundle();
                queryBundle.putString(SEARCH_QUERY_URL_EXTRA, trailURL);

                LoaderManager loaderManager = LoaderManager.getInstance(this);
                Loader<List<Movie>> movieLoader = loaderManager.getLoader(TRAIL_OPTIONS_LOADER);

                if (movieLoader == null) {
                    loaderManager.initLoader(TRAIL_OPTIONS_LOADER, queryBundle, this);
                } else {
                    loaderManager.restartLoader(TRAIL_OPTIONS_LOADER, queryBundle, this);
                }
            } else if (intent.hasExtra(hDBHasValues)) {
                switch (optionValues) {
                    case R.id.sort_difficulty:
                        dbSort = 3;
                        break;
                    case R.id.sort_length:
                        dbSort = 2;
                        break;
                    case R.id.sort_distance:
                    default:
                        dbSort = 1;
                        break;
                }
                loadFavMovieData(dbSort);
            }
        }
    }

    @NonNull
    @Override
    public Loader<List<Hike>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Hike>>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) {
                    return;
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);

                forceLoad();
            }

            @Nullable
            @Override
            public List<Hike> loadInBackground() {
                String hikeTrailsURL = args.getString(SEARCH_QUERY_URL_EXTRA);
                Log.v(LOG_TAG, "URL: " + hikeTrailsURL);

                if (hikeTrailsURL == null || TextUtils.isEmpty(hikeTrailsURL)) {
                    return null;
                }

                URL newURL = null;
                try {
                    newURL = new URL(hikeTrailsURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    String jsonHikeResponse = NetworkUtils
                            .getResponseFromHttpUrl(newURL);

                    List<Hike> simpleJsonHikeData = HikeJasonUtils.getHikeDetailFromJson(
                            TrailOptionActivity.this, jsonHikeResponse, sortType);

                    for (int i = 0; i < simpleJsonHikeData.size(); i++) {
                        simpleJsonHikeData.get(i).setHomeLat(homeLat);
                        simpleJsonHikeData.get(i).setHomeLong(homeLong);
                    }

                    return simpleJsonHikeData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Hike>> loader, List<Hike> hikeData) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (hikeData != null) {
            Throwable throwable = new Throwable();
            throwable.printStackTrace();
            showTrailOptionDataView();
            mHikeDetails = hikeData;
            trailOptionAdapter.setHikeDetails(hikeData);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Hike>> loader) {
        getLoaderManager().destroyLoader(TRAIL_OPTIONS_LOADER);
    }

    @Override
    public void onClick(Hike hikeDetails) {
        Intent intentToStartDetails = new Intent(this, TrailDetailActivity.class);

        hikeDetails.setHomeLat(hikeDetails.getHomeLat());
        hikeDetails.setHomeLong(hikeDetails.getHomeLong());
        intentToStartDetails.putExtra(hikeObject, hikeDetails);
        startActivity(intentToStartDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_type, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        optionID = item.getItemId();

        trailOptionAdapter.setHikeDetails(mHikeDetails);

        loadTrails(optionID);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SORT", optionID);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        optionID = savedInstanceState.getInt("SORT");
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTrails(optionID);
    }

    private void showTrailOptionDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        //trailHeaders.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        //trailHeaders.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showDBErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        //trailHeaders.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setText(R.string.empty_db);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void loadFavMovieData(final int sortType) {
        LiveData<List<HikeEntry>> hikeEntry;
        switch (sortType) {
            case 1:
                hikeEntry = mDB.hikeDAO().loadAllHikesById();
                break;
            case 2:
                hikeEntry = mDB.hikeDAO().loadAllHikesByLength();
                break;
            case 3:
            default:
                hikeEntry = mDB.hikeDAO().loadAllHikesByDiff();
                break;
        }
        
        hikeEntry.observe(this, new Observer<List<HikeEntry>>() {
            @Override
            public void onChanged(@Nullable List<HikeEntry> hikeEntires) {
                if (hikeEntires.isEmpty()) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    showDBErrorMessage();
                } else {
                    final List<Hike> hikeData = new ArrayList<>();
                    for (int i = 0; i < hikeEntires.size(); i++) {
                        Hike newHike = new Hike();
                        newHike.setHikeID(hikeEntires.get(i).getHikeID());
                        newHike.setHikeName(hikeEntires.get(i).getHikeName());
                        newHike.setHikeType(hikeEntires.get(i).getHikeType());
                        newHike.setHikeSummary(hikeEntires.get(i).getHikeSummary());
                        newHike.setHikeDifficulty(hikeEntires.get(i).getHikeDifficulty());
                        newHike.setHikeStars(hikeEntires.get(i).getHikeStars());
                        newHike.setHikeLocation(hikeEntires.get(i).getHikeLocation());

                        newHike.setHikeURL(hikeEntires.get(i).getHikeURL());
                        newHike.setHikeImageSmall(hikeEntires.get(i).getHikeImageSmall());
                        newHike.setHikeImage(hikeEntires.get(i).getHikeImage());
                        newHike.setHikeLength(hikeEntires.get(i).getHikeLength());
                        newHike.setHikeLat(hikeEntires.get(i).getHikeLat());
                        newHike.setHikeLong(hikeEntires.get(i).getHikeLong());
                        newHike.setHikeCond(hikeEntires.get(i).getHikeCond());
                        newHike.setHikeCondDetails(hikeEntires.get(i).getHikeCondDetails());
                        newHike.setHomeLat(hikeEntires.get(i).getHomeLat());
                        newHike.setHomeLong(hikeEntires.get(i).getHomeLong());
                        newHike.setFav(hikeEntires.get(i).getHikeIsFav());

                        hikeData.add(newHike);
                    }
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    trailOptionAdapter.setHikeDetailsFromDB(hikeData);
                    showTrailOptionDataView();
                }
            }
        });

    }
}
