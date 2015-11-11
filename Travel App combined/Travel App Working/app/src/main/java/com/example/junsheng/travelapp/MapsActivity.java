package com.example.junsheng.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private double toLat = 0;
    private double toLon = 0;
    private double fromLat = 0;
    private double fromLon = 0;
    private Marker fromMarker;
    private Marker toMarker;
    private String foundToLocation = "To here!";
    private String foundFromLocation = "Going from here";

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        Switch mapTypeSwitch = (Switch) findViewById(R.id.mapTypeSwitch);

        //set the switch to ON
        mapTypeSwitch.setChecked(true);
        //attach a listener to check for changes in state
        mapTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    mMap.setMapType(mMap.MAP_TYPE_NORMAL); //normal mapType
                } else {
                    mMap.setMapType(mMap.MAP_TYPE_SATELLITE); //satellite mapType
                }

            }
        });

        setUpMapIfNeeded();
        if (mMap != null){
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
///////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                Log.i("troubleshoot","setting up map first time");
                setUpMap();
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.clear();
        Log.i("troubleshoot", "clearing map");

        LatLng toLatLng = new LatLng(toLat,toLon);
        toMarker = mMap.addMarker(new MarkerOptions().position(toLatLng).title(foundToLocation));
        LatLng fromLatLng = new LatLng(fromLat,fromLon);
        fromMarker = mMap.addMarker(new MarkerOptions().position(fromLatLng).title(foundFromLocation));
        Log.i("troubleshoot","added markers");

        drawLine();

        LatLngBounds latLngBounds = computeLatLngBounds(fromLat,fromLon,toLat,toLon);

        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 200));
        } catch (Exception ex){
            ex.printStackTrace();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 500, 500 , 200));
        }
    }

    // Search function that activates when search button is pressed
    public void Search(View view){
        RobustSpellChecker RSC = new RobustSpellChecker();
        Geocoder myGcdrTo = new Geocoder(this);
        List<Address> matchedListTo = null;
        EditText toAddress = (EditText) findViewById(R.id.toAddress);
        foundToLocation = toAddress.getText().toString();
        foundToLocation = RSC.SpellChecker(foundToLocation);
        Geocoder myGcdrFrom = new Geocoder(this);
        List<Address> matchedListFrom = null;
        EditText fromAddress = (EditText) findViewById(R.id.fromAddress);
        foundFromLocation = fromAddress.getText().toString();
        foundFromLocation = RSC.SpellChecker(foundFromLocation);

        try {
            Toast.makeText(getApplicationContext(), "Finding Location...", Toast.LENGTH_LONG).show();
            matchedListTo = myGcdrTo.getFromLocationName(foundToLocation, 1);
            matchedListFrom = myGcdrFrom.getFromLocationName(foundFromLocation, 1);
        } catch (IOException e){
            Toast.makeText(this, "Not able to find location" + e.getMessage(), Toast.LENGTH_LONG ).show();
        }
        Intent openMap = new Intent(getApplicationContext(),MapsActivity.class);

        Log.i("troubleshoot","location found");

        try{
            toLat = Double.parseDouble(String.valueOf(matchedListTo.get(0).getLatitude()));
            toLon = Double.parseDouble(String.valueOf(matchedListTo.get(0).getLongitude()));
            fromLat = Double.parseDouble(String.valueOf(matchedListFrom.get(0).getLatitude()));
            fromLon = Double.parseDouble(String.valueOf(matchedListFrom.get(0).getLongitude()));
        } catch (Exception e){
            Toast.makeText(this, "A problem occured in retrieving location", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("troubleshoot", "lat and lon found");
        setUpMap();
        Log.i("troubleshoot", "exit search after settingupmap");

        // Check if no view has focus:
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        };
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
         /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                 /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    private void handleNewLocation(Location location) {
        if (foundToLocation == "To here!" && foundFromLocation == "Going from here"){
            Log.d(TAG, location.toString());
            mMap.clear();
            double currentLatitude = location.getLatitude();
            double currentLongitude = location.getLongitude();
            LatLng latLng = new LatLng(currentLatitude, currentLongitude);
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(foundFromLocation);
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
        } else {
            setUpMap();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    LatLngBounds computeLatLngBounds(double fromLat, double fromLon, double toLat, double toLon) {
        double greaterLat;
        double smallerLat;
        double greaterLon;
        double smallerLon;
        LatLngBounds latLngBounds;
        if (fromLat >= toLat){
            greaterLat = fromLat;
            smallerLat = toLat;
        } else {
            greaterLat = toLat;
            smallerLat = fromLat;
        }
        if (fromLon >= toLon){
            greaterLon = fromLon;
            smallerLon = toLon;
        } else {
            greaterLon = toLon;
            smallerLon = fromLon;
        }
        LatLng southwest = new LatLng(smallerLat,smallerLon);
        LatLng northeast = new LatLng(greaterLat,greaterLon);
        latLngBounds = new LatLngBounds(southwest, northeast);
        return latLngBounds;
    }

    private void drawLine(){
        PolylineOptions options = new PolylineOptions()
                .add(fromMarker.getPosition())
                .add(toMarker.getPosition());

        mMap.addPolyline(options);
    }

}
