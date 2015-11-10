package com.example.aqi.travelapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class ItineraryActivity extends AppCompatActivity {

    public static final String FILENAME = "saveditineraries.txt";

    public static ArrayList<SavedItinerary> saveditineraries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Loads saved itineraries from file
     * Lazily loaded when user first chooses to add a place to an existing
     * or new itinerary. Never loaded after that (since the itineraries are held
     * in memory as an arraylist)
     * @param context
     */
    public static void loadSavedItineraries(Context context)
    {
        if(saveditineraries == null)
            saveditineraries = FileUtils.readFile(context, FILENAME);

        if(saveditineraries==null)
            saveditineraries = new ArrayList<SavedItinerary>();
    }

    /**
     * Writes saved itinerareis to file
     * Will only be done when app activities are stopped
     * @param context
     */
    public static void writeSavedItineraries(Context context)
    {
        ArrayList<ArrayList<String>> itineraries = new ArrayList<ArrayList<String>>();
        if(saveditineraries != null)
        {
            for(SavedItinerary it: saveditineraries)
            {
                itineraries.add(it.convertToFileOutput());
            }
            FileUtils.writeToFile(context, FILENAME, itineraries);
        }
    }
    /**
     * Adds a new itinerary to the list of saveditineraries
     * Called when user clicks OK after choosing to add a place
     * to a new itinerary
     * @param name name of itinerary
     * @param placename name of new place
     */
    public static void saveNewItinerary(String name, String placename)
    {
        ArrayList<String> destinations = new ArrayList<String>(Arrays.asList(placename));
        ArrayList<String> itinerary = new ArrayList<String>(Arrays.asList(placename));
        int[] transportmodes = {0};
        SavedItinerary savedit = new SavedItinerary(name,destinations,
                itinerary, transportmodes);
        //Add new itinerary
        saveditineraries.add(savedit);
    }

    /**
     * Returns the an array of itinerary names in saveditineraries
     * @return
     */
    public static String[] extractSavedItineraries()
    {
        String[] itineraries = new String[saveditineraries.size()];
        for(int i =0; i< saveditineraries.size();i++)
        {
            itineraries[i] = saveditineraries.get(i).name;
        }

        return itineraries;
    }

    /**
     * Updates a SavedItinerary specified with name with a new
     * destination node (itinerary and transportmodes are only updated
     * after user chooses to plan itinerary again)
     * @param name name of itinerary
     * @param placename name of new destination
     */
    public static void updateSavedItinerary(String name, String placename)
    {
        SavedItinerary itinerary;
        for(int i =0; i< saveditineraries.size();i++)
        {
            if((itinerary = saveditineraries.get(i)).name == name)
            {
               itinerary.destinations.add(placename);
            }
        }
    }

}
