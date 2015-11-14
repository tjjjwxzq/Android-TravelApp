package com.example.junsheng.travelapp;


import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    MapView mMapView;
    private GoogleMap googleMap;

    private ArrayList<Double> lon;
    private ArrayList<Double> lat;
    private ArrayList<String> locations;
    private ArrayList<Integer> transportModes;
    private String foundToLocation = "To here!";
    private String foundFromLocation = "Going from here";
    private int checkpoints;
    Marker from;
    Marker to;

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MainActivityTest.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.activity_map_fragment, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        googleMap = mMapView.getMap();

        if (googleMap != null){
            googleMap.setMyLocationEnabled(true);
        }
        return v;
    }

    // Help me to compute the Bounds for start and end points
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

    // method to draw the route, include different colours for different modes of transportation
    private void drawLine(Marker fromMarker, Marker toMarker, int colorNum){
        int color;
        if (colorNum == 1){
            color = 0xffff0000;             //1,red,public transport
        } else if (colorNum == 2){
            color = 0xff00ff00;             //2,green,walking
        } else {
            color = 0xffffff00;             //3,yellow,taxi
        }
        PolylineOptions options = new PolylineOptions()
                .add(fromMarker.getPosition())
                .add(toMarker.getPosition())
                .color(color);

        googleMap.addPolyline(options);
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
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
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

    // When the Map is first created, it locates and display your current location in this method
    private void handleNewLocation(Location location) {
        if (foundToLocation == "To here!" && foundFromLocation == "Going from here"){
            Log.d(TAG, location.toString());
            googleMap.clear();
            double currentLatitude = location.getLatitude();
            double currentLongitude = location.getLongitude();
            LatLng latLng = new LatLng(currentLatitude, currentLongitude);
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(foundFromLocation);
            googleMap.addMarker(options);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        mMapView.onLowMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    // allows the activity to update the fragment
    public void update(ArrayList<Double> longitude,ArrayList<Double> latitude, ArrayList<String> Locations, ArrayList<Integer> Transports, int Checkpoints){
        lon=longitude;
        lat=latitude;
        locations=Locations;
        transportModes=Transports;
        foundToLocation = locations.get(locations.size()-1);
        foundFromLocation = locations.get(0);
        checkpoints = Checkpoints;

        googleMap.clear();
        for (int i = 0;i<checkpoints;i++){
            // from and to are markers
            to = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat.get(i), lon.get(i))).title(locations.get(i)));
            if (i>0){
                Log.i("Test",String.valueOf(transportModes.get(i)));
                drawLine(from, to, transportModes.get(i));
            }
            from = to;
        }

        LatLngBounds latLngBounds = computeLatLngBounds(lat.get(0),lon.get(0),lat.get(lat.size()-1),lon.get(lon.size()-1));

        try {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 200));
        } catch (Exception ex){
            ex.printStackTrace();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 500, 500 , 200));
        }
    }

    public void setNormalMap(){
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL); //normal mapType
    }

    public void setSatelliteMap(){
        googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE); //satellite mapType
    }

}