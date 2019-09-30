package com.chandu.chandu_udacity_capstone.HikeActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.chandu.chandu_udacity_capstone.Database.HikeDatabase;
import com.chandu.chandu_udacity_capstone.Database.HikeEntry;
import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.Utilities.AppExecutors;
import com.chandu.chandu_udacity_capstone.Widgets.TrailWidgetProvider;
import com.chandu.chandu_udacity_capstone.hike.Hike;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailDetailActivity extends AppCompatActivity {
    @BindView(R.id.hike_name)
    TextView mHikeName;
    @BindView(R.id.hike_summary)
    TextView mHikeSummary;
    @BindView(R.id.hike_image)
    ImageView mHikeImage;
    @BindView(R.id.hike_condition)
    TextView mHikeCond;
    @BindView(R.id.hike_url)
    Button mHikeURL;
    @BindView(R.id.hike_ratings)
    TextView mHikeRate;
    @BindView(R.id.fav_button)
    ToggleButton mFavHike;
    @BindView(R.id.map_fab)
    FloatingActionButton mapFab;
    @BindView(R.id.ctoolbar)
    Toolbar myToolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout cLayout;
    private HikeDatabase mHDb;
    LiveData<HikeEntry> currentHikeIsFav;
    final String trailObject = "trailObject";
    final String hikeObject = "hikeObject";
    private Hike hikeDetails;
    private String trailName = "TEST Trail";
    LatLng p1 = null;
    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    String latLongDetails = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_more_details);

        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        cLayout.setTitle("");


        mHDb = HikeDatabase.getsInstance(getApplicationContext());

        mapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStartDetails = new Intent(getApplicationContext(), ImageToMapActivity.class);

                intentToStartDetails.putExtra(hikeObject, hikeDetails);
                startActivity(intentToStartDetails);
            }
        });

        mHikeURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mHikeURL.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        updateHikeDetails();

        onSaveHike();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void updateHikeDetails() {
        Intent intent = getIntent();
        if ((intent != null) && intent.hasExtra(hikeObject)) {
            hikeDetails = (Hike) intent.getSerializableExtra(hikeObject);
            trailName = hikeDetails.getHikeName();
            mHikeName.setText(trailName);

            if (hikeDetails != null) {
                if (hikeDetails.getHikeImage() != null && hikeDetails.getHikeImage() != "") {
                    Picasso.get().load(hikeDetails.getHikeImage()).into(mHikeImage);
                } else {
                    mHikeImage.setBackgroundResource(R.drawable.trail_icons);
                    //mHikeImage.setVisibility(View.INVISIBLE);
                }

                if (hikeDetails.getHikeCond() != null && !hikeDetails.getHikeCond().equals("Unknown")
                        && hikeDetails.getHikeCondDetails() != null &&
                        !hikeDetails.getHikeCondDetails().equals("null")) {
                    mHikeCond.setText("Conditions: \n" + hikeDetails.getHikeCond() + " : " + hikeDetails.getHikeCondDetails());
                } else {
                    mHikeCond.setText("Conditions: N/A");
                }

                if (hikeDetails.getHikeSummary() != null && hikeDetails.getHikeSummary() != "") {
                    mHikeSummary.setText("Summary: \n" + hikeDetails.getHikeSummary());
                } else {
                    mHikeSummary.setText("Summary: N/A");
                }

                if (hikeDetails.getHikeStars() != null && hikeDetails.getHikeStars() != "") {
                    mHikeRate.setText("Ratings: " + hikeDetails.getHikeStars());
                } else {
                    mHikeRate.setText("Ratings: N/A");
                }

                if (hikeDetails.getHikeURL() != null && hikeDetails.getHikeURL() != "") {
                    mHikeURL.setText(hikeDetails.getHikeURL());
                } else {
                    mHikeURL.setVisibility(View.INVISIBLE);
                }
                
                setFavButton();
            }
        }
    }

    private void setFavButton() {
        Context context = getApplicationContext();
        currentHikeIsFav = mHDb.hikeDAO().loadFavHikeId(hikeDetails.getHikeID());
        currentHikeIsFav.observe(this, new Observer<HikeEntry>() {
            @Override
            public void onChanged(@Nullable HikeEntry currentHike) {
                if (currentHike != null && currentHike.getHikeIsFav()) {
                    mFavHike.setTextOn("Remove");
                    mFavHike.setChecked(true);
                    hikeDetails.setFav(true);
                } else {
                    mFavHike.setTextOff("Add");
                }
            }
        });

    }
    
    public void onSaveHike() {
        final Animation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        Log.d("onSaveHike", "New movie is " + hikeDetails.getHikeName());

        mFavHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFavHike.isChecked()) {
                    mFavHike.setTextOn("Remove");
                    mFavHike.setChecked(true);
                    hikeDetails.setFav(true);
                    mFavHike.startAnimation(scaleAnimation);
                    Log.d("onSaveHike", "Added hike is " + hikeDetails.getHikeName());
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            final HikeEntry hikeEntry = new HikeEntry(hikeDetails.getHikeID(), 
                                    hikeDetails.getHikeName(), hikeDetails.getHikeType(),
                                    hikeDetails.getHikeSummary(), hikeDetails.getHikeDifficulty(),
                                    hikeDetails.getHikeStars(), hikeDetails.getHikeLocation(),
                                    hikeDetails.getHikeURL(), hikeDetails.getHikeImageSmall(),
                                    hikeDetails.getHikeImage(), hikeDetails.getHikeLength(),
                                    hikeDetails.getHikeLat(), hikeDetails.getHikeLong(),
                                    hikeDetails.getHikeCond(), hikeDetails.getHikeCondDetails(),
                                    hikeDetails.getHomeLat(), hikeDetails.getHomeLong(), 
                                    hikeDetails.getFav());
                                    
                            mHDb.hikeDAO().insertHike(hikeEntry);
                            updateWidget();
                        }
                    });

                } else {
                    mFavHike.setTextOff("Add");
                    mFavHike.setChecked(false);
                    Log.d("onSaveHike", "Deleted hike is " + hikeDetails.getHikeName());
                    hikeDetails.setFav(false);
                    mFavHike.startAnimation(scaleAnimation);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mHDb.hikeDAO().deleteHike(mHDb.hikeDAO().loadHikeById(hikeDetails.getHikeID()));
                            updateWidget();
                        }
                    });
                }

            }

        });
    }

    private void updateWidget() {
        final List<HikeEntry> hikeList = mHDb.hikeDAONL().loadAllHikesById();

        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), TrailWidgetProvider.class));
        TrailWidgetProvider myWidget = new TrailWidgetProvider();
        myWidget.updateTrailWidget(getApplicationContext(), AppWidgetManager.getInstance(getApplicationContext()), hikeList, ids);
    }

}
