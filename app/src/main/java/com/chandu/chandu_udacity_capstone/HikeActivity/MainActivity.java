package com.chandu.chandu_udacity_capstone.HikeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chandu.chandu_udacity_capstone.BuildConfig;
import com.chandu.chandu_udacity_capstone.Database.HikeDatabase;
import com.chandu.chandu_udacity_capstone.Database.HikeDatabase_Impl;
import com.chandu.chandu_udacity_capstone.Database.HikeEntry;
import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.Utilities.HikeJasonUtils;
import com.chandu.chandu_udacity_capstone.Utilities.StateJasonUtils;
import com.chandu.chandu_udacity_capstone.hike.States;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements StatesAdapter.StatesAdapterOnClickHandler {
    @BindView(R.id.recyclerview_stateslist)
    RecyclerView mRecyclerView;
    @BindView(R.id.states_loading_indicator)
    ProgressBar statePB;
    @BindView(R.id.states_error_message_display)
    TextView statesError;
    @BindView(R.id.static_spinner)
    Spinner stateSpinner;
    @BindView(R.id.main_loading_indicator)
    ProgressBar mainPB;
    @BindView(R.id.fab)
    FloatingActionButton searchFab;

    String TAG = "placeautocomplete";
    LatLng p1 = null;
    private StatesAdapter stateAdapter;
    final String trailLat = "trailLat";
    final String trailLong = "trailLong";
    final String hDBHasValues = "trailsInDB";
    String latLongDetails = "";
    Double lat;
    Double lon;
    private static final String SEARCH_QUERY_URL_EXTRA = "location";
    private static final String trail_error = "An error has occurred while retrieving hike options.";
    private static final int TRAIL_OPTIONS_LOADER = 11;
    private static final int STATE_OPTIONS_LOADER = 33;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private HikeDatabase mHDb;
    private boolean savedButtonHandled= false;
    Parcelable savedRecyclerLayoutState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();
        ButterKnife.bind(this);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, calculateNoOfColumns(this));

        mRecyclerView.setLayoutManager(layoutManager);

        stateAdapter = new StatesAdapter(this);
        mRecyclerView.setAdapter(stateAdapter);

        // Initialize Places.
        Places.initialize(getApplicationContext(), BuildConfig.GoogleApiKey);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        mHDb = HikeDatabase.getsInstance(getApplicationContext());

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStartDetails = new Intent(getApplicationContext(), GoogleLocationSearch.class);
                startActivity(intentToStartDetails);
            }
        });
        stateSpinner.setEnabled(true);
        stateSpinner.setSelection(0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_trails, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Now apply the adapter to spinner
        stateSpinner.setAdapter(adapter);

        //............spinner item selected listener........
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    String selectedItemText = parent.getItemAtPosition(pos).toString();

                    Bundle queryBundle = new Bundle();
                    queryBundle.putString(SEARCH_QUERY_URL_EXTRA, selectedItemText);

                    LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);
                    Loader<LatLng> stateLoader = loaderManager.getLoader(TRAIL_OPTIONS_LOADER);

                    if (stateLoader == null) {
                        loaderManager.initLoader(TRAIL_OPTIONS_LOADER, queryBundle, new SpinnerHikesLoader());
                    } else {
                        loaderManager.restartLoader(TRAIL_OPTIONS_LOADER, queryBundle, new SpinnerHikesLoader());
                    }
                } else {
                    getLoaderManager().destroyLoader(TRAIL_OPTIONS_LOADER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
                return;
            }

        });

        updateStateImages();
    }

    private void updateStateImages() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, "None");

        LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);
        Loader<LatLng> stateLoader = loaderManager.getLoader(STATE_OPTIONS_LOADER);

        if (stateLoader == null) {
            loaderManager.initLoader(STATE_OPTIONS_LOADER, queryBundle, new StatesHikesLoader());
        } else {
            loaderManager.restartLoader(STATE_OPTIONS_LOADER, queryBundle, new StatesHikesLoader());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        stateSpinner.setSelection(0);
        updateStateImages();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getLoaderManager().destroyLoader(TRAIL_OPTIONS_LOADER);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getLoaderManager().destroyLoader(TRAIL_OPTIONS_LOADER);
        //savedTrails.setEnabled(false);
    }

    public void generateTrailList(final Double latVal, final Double longVal) {
        Intent intentToStartDetails = new Intent(getApplicationContext(), TrailOptionActivity.class);
        intentToStartDetails.putExtra(trailLat, Double.toString(latVal));
        intentToStartDetails.putExtra(trailLong, Double.toString(longVal));
        startActivity(intentToStartDetails);
    }

    public class SpinnerHikesLoader implements LoaderManager.LoaderCallbacks<LatLng> {

        @NonNull
        @Override
        public Loader<LatLng> onCreateLoader(int id, @Nullable Bundle args) {
            return new AsyncTaskLoader<LatLng>(getApplicationContext()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    mainPB.setVisibility(View.VISIBLE);
                    //mainError.setVisibility(View.INVISIBLE);
                    forceLoad();
                }

                @Nullable
                @Override
                public LatLng loadInBackground() {
                    LatLng l1 = new LatLng(0, 0);
                    String hikeState = args.getString(SEARCH_QUERY_URL_EXTRA);
                    Log.v(LOG_TAG, "Location Details: " + hikeState);

                    if (hikeState != null && !hikeState.isEmpty()) {
                        try {
                            List<Address> result = new Geocoder(getApplicationContext()).getFromLocationName(
                                    hikeState, 1);
                            LatLng spinnerList = new LatLng(result.get(0).getLatitude(),
                                    result.get(0).getLongitude());
                            args.remove(SEARCH_QUERY_URL_EXTRA);
                            return spinnerList;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    return l1;
                }

                @Override
                protected void onStopLoading() {
                    super.onStopLoading();
                    cancelLoad();
                }
            };
        }

        @Override
        public void onLoadFinished(@NonNull Loader<LatLng> loader, LatLng data) {
            mainPB.setVisibility(View.INVISIBLE);
            //mainError.setVisibility(View.INVISIBLE);
            if (data != null) {

                if (data.latitude != 0 && data.latitude != 0) {
                    lat = data.latitude;
                    lon = data.longitude;
                    data = null;
                    generateTrailList(lat, lon);
                }
            } else {
                showError();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<LatLng> loader) {
            getLoaderManager().destroyLoader(TRAIL_OPTIONS_LOADER);
        }
    }

    public class StatesHikesLoader implements LoaderManager.LoaderCallbacks<List<States>> {

        @NonNull
        @Override
        public Loader<List<States>> onCreateLoader(int id, @Nullable Bundle args) {
            return new AsyncTaskLoader<List<States>>(getApplicationContext()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if (args == null) {
                        return;
                    }
                    statePB.setVisibility(View.VISIBLE);

                    forceLoad();
                }

                @Nullable
                @Override
                public List<States> loadInBackground() {
                    String statesJasonText = StateJasonUtils.loadJSONFromAsset(getApplicationContext());

                    try {
                        return StateJasonUtils.getStateDetailFromJson(getApplicationContext(),
                                statesJasonText);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<States>> loader, List<States> stateData) {
            statePB.setVisibility(View.INVISIBLE);
            if (stateData != null) {
                Throwable throwable = new Throwable();
                throwable.printStackTrace();
                showStateDataView();
                stateAdapter.setStatesDetails(stateData);
                mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            } else {
                showErrorMessage();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<States>> loader) {

        }
    }

    private void showStateDataView() {
        /* First, make sure the error is invisible */
        statesError.setVisibility(View.INVISIBLE);
        /* Then, make sure the State data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        statesError.setVisibility(View.VISIBLE);
    }

    public void saveButtonHandler() {
        final LiveData<List<HikeEntry>> movieEntry = mHDb.hikeDAO().loadAllHikesById();
        movieEntry.observe(MainActivity.this, new Observer<List<HikeEntry>>() {
            @Override
            public void onChanged(@Nullable List<HikeEntry> hikeEntires) {
                savedButtonHandled = true;

                if (hikeEntires.isEmpty()) {
                    mainPB.setVisibility(View.INVISIBLE);
                    showError();
                } else {
                    Intent intentToStartDetails = new Intent(getApplicationContext(), TrailOptionActivity.class);
                    intentToStartDetails.putExtra(hDBHasValues, "true");
                    startActivity(intentToStartDetails);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.saved_button, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.saved_button) {
            finish();
            return true;
        }

        if (!savedButtonHandled) {
            saveButtonHandler();
        } else {
            savedButtonHandled = false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showError() {
        Toast.makeText(MainActivity.this, trail_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(States statesDetails) {
        String placeState = "";
        if (statesDetails != null) {
            placeState = statesDetails.getCapName() +"," + statesDetails.getStateName();

            Bundle queryBundle = new Bundle();
            queryBundle.putString(SEARCH_QUERY_URL_EXTRA, placeState);

            LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);
            Loader<LatLng> stateLoader;
            stateLoader = loaderManager.getLoader(TRAIL_OPTIONS_LOADER);

            if (stateLoader == null) {
                loaderManager.initLoader(TRAIL_OPTIONS_LOADER, queryBundle, new SpinnerHikesLoader());
            } else {
                loaderManager.restartLoader(TRAIL_OPTIONS_LOADER, queryBundle, new SpinnerHikesLoader());
            }
        }
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns <= 2)
            noOfColumns = 1;
        return noOfColumns;
    }
}

