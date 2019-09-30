package com.chandu.chandu_udacity_capstone.HikeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.hike.Hike;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageToMapActivity extends AppCompatActivity implements OnMapReadyCallback,
                                            GoogleApiClient.ConnectionCallbacks,
                                            GoogleApiClient.OnConnectionFailedListener {
    LatLng p1 = null;
    LatLng p2 = null;
    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    final String hikeObject = "hikeObject";
    private Hike hikeDetails;
    private int ZOOM_LEVEL = 5;
    private static final int TILT_LEVEL = 0;
    private static final int BEARING_LEVEL = 0;
    Location destinationLoc;
    Location homeLoc;
    float distanceBtw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_map);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if ((intent != null) && intent.hasExtra(hikeObject)) {
            hikeDetails = (Hike) intent.getSerializableExtra(hikeObject);
            Double lat = Double.parseDouble(hikeDetails.getHikeLat());
            Double lon = Double.parseDouble(hikeDetails.getHikeLong());

            p1 = new LatLng(lat, lon);
            destinationLoc = new Location("Destination Trail");
            destinationLoc.setLatitude(lat);
            destinationLoc.setLongitude(lon);

            if (hikeDetails.getHomeLat() != null && !hikeDetails.getHomeLat().equals("") &&
                    hikeDetails.getHomeLong() != null && !hikeDetails.getHomeLong().equals("")) {
                lat = Double.parseDouble(hikeDetails.getHomeLat());
                lon = Double.parseDouble(hikeDetails.getHomeLong());
                p2 = new LatLng(lat, lon);
                homeLoc = new Location("Home Address");
                homeLoc.setLatitude(lat);
                homeLoc.setLongitude(lon);
                distanceBtw = Float.valueOf(destinationLoc.distanceTo(homeLoc));
            } else {
                distanceBtw = Float.valueOf("0.0");
            }

        } else {
            distanceBtw = Float.valueOf("0.0");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng bbsr, bbsr1;
        BitmapDescriptor bitmapDescriptor
                = BitmapDescriptorFactory.fromResource(
                (int) BitmapDescriptorFactory.HUE_AZURE);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        if (p1 != null && p2!= null) {
            // Add a marker in Sydney and move the camera
            bbsr = new LatLng(p1.latitude, p1.longitude);
            bbsr1 = new LatLng(p2.latitude, p2.longitude);
        } else {
            //mMap.setMyLocationEnabled(true);
            bbsr = new LatLng(33.7490, -84.3880);
            bbsr1 = new LatLng(33.7490, -84.3880);
        }

        double circleRed = Double.parseDouble(Float.valueOf(distanceBtw).toString());

        ZOOM_LEVEL = getZoomLevel(circleRed);

        mMap.addMarker(new MarkerOptions().position(bbsr).title("Marker at trail"));
        CameraPosition camPos = new CameraPosition(bbsr, ZOOM_LEVEL, TILT_LEVEL, BEARING_LEVEL);

        mMap.addMarker(new MarkerOptions().position(bbsr1).title("Marker at Home").icon(BitmapDescriptorFactory.fromResource(R.drawable.home_icon)));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camPos));

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public int getZoomLevel(double radius) {
        double scale = radius / 500;
        return ((int) (16 - Math.log(scale) / Math.log(2)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intentToStartDetails = new Intent(this, TrailDetailActivity.class);
                intentToStartDetails.putExtra(hikeObject, hikeDetails);
                startActivity(intentToStartDetails);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
