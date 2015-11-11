package com.example.aqi.travelapp;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Arrays;

public class ItineraryActivity {

    private static final String TAG = "ItineraryActivity";

    public static final String FILENAME = "saveditineraries.txt";

    public static ArrayList<SavedItinerary> saveditineraries;

    /*@Override
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
*/
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
        Log.d(TAG, "Writing to file");
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
        ArrayList<String> itinerary = new ArrayList<String>();
        itinerary.add(placename);
        int[] transportmodes = {0};
        SavedItinerary savedit = new SavedItinerary(name,destinations,
                itinerary, transportmodes);
        //Add new itinerary
        saveditineraries.add(savedit);
        Log.d(TAG, "saved itineraries " + saveditineraries);
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

        Log.d(TAG, "extract saved itineraries " + saveditineraries);
        return itineraries;
    }

    /**
     * Updates a SavedItinerary specified with name with a new
     * destination node (itinerary and transportmodes are only updated
     * after user chooses to plan itinerary again)
     * Should check that the itinerary doesn't already contain
     * the destination with placename
     * @param name name of itinerary
     * @param placename name of new destination
     * @return 1 indicates placename was added, 0 that it was not (duplicate)
     */
    public static int updateSavedItinerary(String name, String placename) {
        SavedItinerary itinerary;
        //Use Guava Collections2.filter to get the collection filtered
        //according to SavedItinerary.name
        final String fname = name;
        ArrayList<SavedItinerary> arrsavedit = new ArrayList<SavedItinerary>(
                Collections2.filter(saveditineraries,
                        new Predicate<SavedItinerary>() {
                            @Override
                            public boolean apply(SavedItinerary input) {
                                return input.name.equals(fname);
                            }
                        }));

        //Updated the filtered itinerary, which should have contain a pointer
        //to the original SavedItinerary object
        //But only if the itinerary doesn't already contain a destination with placename
        if (!arrsavedit.get(0).destinations.contains(placename))
        {
            arrsavedit.get(0).destinations.add(placename);
            return 1;
        }

        return 0;
    }

}
