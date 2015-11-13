package com.example.aqi.travelapp;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ItineraryManager{

    private static final String TAG = "ItineraryManager";

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
        ArrayList<String> destinations = new ArrayList<String>();
        destinations.add(placename);
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
        Log.d(TAG, "exdtract saved it, itienrary" + saveditineraries.get(0).itinerary);
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
            Log.d(TAG, "Adding place to destinations, does itineary also change?");
            Log.d(TAG, ""+arrsavedit.get(0).itinerary);
            return 1;
        }

        return 0;
    }

    /**
     * Checks if the destination String has valid cost data by seeing
     * if it matches closely enough with the HashMap stored in Destinations
     * If yes return the version of the destination name stored in the HashMap
     * @param destination
     * @return
     */
    public static String checkDestination(String destination)
    {
        for(String key: Destinations.DESTINATION_MAP.keySet())
        {
            if(FuzzyStringMatcher.computeFuzzyScore(destination, key)>0.9)
            {
                Log.d(TAG, "this key " + key + " and node " + destination);
                Log.d(TAG, "fuzzy score " + FuzzyStringMatcher.computeFuzzyScore(destination,key));
                return key;
            }
        }

        return null;

    }


    /**
     * Plans an itinerary using either CrappyBruteSolver or FastApproxSolver as
     * defined by the parameter method, given an arraylist of destinations
     * Checks each destination to see if there is valid cost data for it;
     * if not then return null
     * @param method choose to use CrappyBruteSolver or FastApproxSolver
     * @param destinations list of destinations
     * @return returns the itinerary or null if no valid cost-time data
     */
    public static ArrayList<String> planItinerary(int method, ArrayList<String> destinations)
    {
        //List of destinations with properly formatted names
        ArrayList<String> properdestinations = new ArrayList<String>();

        for(String destination : destinations)
        {
            String node = checkDestination(destination);
            if(node!=null) {
                Log.d(TAG, "adding proper destinations");
                properdestinations.add(node);
            }
            else
                return null; //if one of the nodes doesn't have valid data, itinerary can't be planned
        }

        ArrayList<String> fastit;
        ArrayList<String> slowit;
        fastit = FastApproxSolver.getTwoOpt(FastApproxSolver.planItineraryNN(properdestinations));
        //slowit = CrappyBruteSolver.getOptimalItinerary(CrappyBruteSolver.generatePermutations(properdestinations));
        return method==0?fastit:fastit;
    }

}
