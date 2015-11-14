package com.example.junsheng.travelapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityTest extends ActionBarActivity {

    private MapFragment fragment;

    private ArrayList<Double> lon = new ArrayList<Double>(Arrays.asList(0.0,0.0));
    private ArrayList<Double> lat = new ArrayList<Double>(Arrays.asList(0.0,0.0));
    private ArrayList<Integer> transportModes = new ArrayList<Integer>(Arrays.asList(1,2));
    private ArrayList<String> locations = new ArrayList<String>(Arrays.asList("Going from here", "To here!"));
    private String foundToLocation;
    private String foundFromLocation;

    private int checkpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_test);

        Switch mapTypeSwitch = (Switch) findViewById(R.id.mapTypeSwitch);

        //set the switch to ON
        mapTypeSwitch.setChecked(true);
        //attach a listener to check for changes in state
        mapTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    fragment.setNormalMap();
                } else {
                    fragment.setSatelliteMap();
                }

            }
        });

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment = new MapFragment();
        transaction.add(R.id.myMapFragment,fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpMap() {
        //Update the fragment
        fragment.update(lon,lat,locations,transportModes,checkpoints);
    }

    // Search function that activates when search button is pressed
    public void Search(View view){

        //Reset!
        locations = new ArrayList<String>();
        lon = new ArrayList<Double>();
        lat = new ArrayList<Double>();
        transportModes = new ArrayList<Integer>();

        //Spell Check
        RobustSpellChecker RSC = new RobustSpellChecker();
        EditText toAddress = (EditText) findViewById(R.id.toAddress);
        foundToLocation = toAddress.getText().toString();
        foundToLocation = RSC.SpellChecker(foundToLocation);
        EditText fromAddress = (EditText) findViewById(R.id.fromAddress);
        foundFromLocation = fromAddress.getText().toString();
        foundFromLocation = RSC.SpellChecker(foundFromLocation);

        ////////////////////////////////////////////////////////////////
        //Compute destinations here and return checkpoints in list    //
        ////////////////////////////////////////////////////////////////

        //pass the locations and transport here

        /////////////////////////////////////////////////////////////Use this to test multiple locations///////////////////////////////////////////////////////////////////////
//        locations = new ArrayList<String>(Arrays.asList("Resort World Sentosa", "Marina Bay Sands", "Singapore Zoo", "Jurong Bird Park", "Esplanade", "Resort World Sentosa"));
//        transportModes = new ArrayList<Integer>(Arrays.asList(1,2,3,1,2,3));
//        checkpoints = locations.size();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////Use this for the 2 locations/////////////
        locations.add(foundFromLocation);
        transportModes.add(1);
        locations.add(foundToLocation);
        transportModes.add(2);
        checkpoints = locations.size();
        ///////////////////////////////////////////////////

        //Get lat and lon
        try {
            Toast.makeText(getApplicationContext(), "Finding Location...", Toast.LENGTH_LONG).show();

            for (int i = 0; i < checkpoints; i++) {
                List<Address> matchedList = null;
                Geocoder myGcdr = new Geocoder(this);
                matchedList = myGcdr.getFromLocationName(locations.get(i) + " Singapore", 1,1.251484, 103.618240,1.464026,104.110222);
                Double Lat = Double.parseDouble(String.valueOf(matchedList.get(0).getLatitude()));
                Double Lon = Double.parseDouble(String.valueOf(matchedList.get(0).getLongitude()));
                lat.add(Lat);
                lon.add(Lon);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Not able to find location" + e.getMessage(), Toast.LENGTH_LONG ).show();
        } catch (Exception e) {
            Toast.makeText(this, "A problem occured in retrieving location", Toast.LENGTH_LONG).show();
            return;
        }

        setUpMap();

        // Check if no view has focus
        // This makes sure that the soft keyboard hides after hitting the search
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
