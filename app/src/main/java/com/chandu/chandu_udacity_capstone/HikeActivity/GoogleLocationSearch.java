package com.chandu.chandu_udacity_capstone.HikeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chandu.chandu_udacity_capstone.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class GoogleLocationSearch extends AppCompatActivity {
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
    private static final String LOG_TAG = GoogleLocationSearch.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_location_search);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        View autoCompleteView = autocompleteFragment.getView();
        autoCompleteView.setBackgroundColor(Color.WHITE);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS));
        autocompleteFragment.setCountry("US");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                p1 = place.getLatLng();

                if (p1 != null) {
                    Log.i(TAG, "Lat: " + p1.latitude + " Long:" + p1.longitude);
                    latLongDetails = "lat=" + p1.latitude + "&" + "lon=" + p1.longitude;
                    generateTrailList(p1.latitude, p1.longitude);
                } else {
                    Log.i(TAG, "Lat Long are empty");
                    latLongDetails = "";
                }
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getAddressComponents().toString());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    public void generateTrailList(final Double latVal, final Double longVal) {
        Intent intentToStartDetails = new Intent(getApplicationContext(), TrailOptionActivity.class);
        intentToStartDetails.putExtra(trailLat, Double.toString(latVal));
        intentToStartDetails.putExtra(trailLong, Double.toString(longVal));
        startActivity(intentToStartDetails);
    }
}
