package com.example.aqi.travelapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    {

    private static final String TAG = "MainActivity";

    private static final String FRAG_ATTRLOC = "AttractionLocatorFragment";

    private static final String FRAG_BUDGET = "BudgetManagerFragment";

    private ArrayAdapter<String> mDrawerAdapter;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    private FragmentManager fragmentmanager;

    private Fragment frag_attrloc;

    final String[] osArray = { "Attraction Locator", "Itinerary Planner", "Budget Manager"};

    // For maps
    public static Search searchlistner;
    //Google Maps
    public static MapFragment mapfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        //Set up navigation drawer
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Start MapFragment
        mapfragment = new MapFragment();
        fragmentmanager = getFragmentManager();
        FragmentTransaction transaction = fragmentmanager.beginTransaction();
        Log.d(TAG, "map fragment " + mapfragment);
        //Tag the fragment so it can be removed from MainActivity
        transaction.replace(R.id.myMapFragment, mapfragment, "MapFragment");

        //Initalize attraction locator fragment and map fragment
        frag_attrloc = new AttractionLocatorFragment();
        transaction.replace(R.id.relative, frag_attrloc, FRAG_ATTRLOC);
        transaction.addToBackStack(FRAG_ATTRLOC);
        transaction.commit();

        //Initialize search listener
        searchlistner = new Search();

        //Initialize the ExpItem array and budget info from the file
        ArrayList<Object> budgetInfo = FileUtils.readBudgetFromFile(this, BudgetManager.BUDGET_FILENAME);
        BudgetManager.totalBudget = ((double[]) budgetInfo.get(0))[0];
        BudgetManager.totalSpent = ((double[]) budgetInfo.get(0))[1];
        BudgetManager.totalRemaining = ((double[]) budgetInfo.get(0))[2];
        BudgetManager.expItemsArr = (ArrayList<ExpItem>) budgetInfo.get(1);



    }

        /**
         * Set up the items in the navigation drawer
         */
    private void addDrawerItems(){

        ItineraryManager.loadSavedItineraries(this); //ensure the file is loaded
        mDrawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mDrawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment;
                FragmentTransaction transaction = fragmentmanager.beginTransaction();
                switch (position) {
                    case 0:
                        //pops till the named fragment, non-inclusive
                        //this will be the first AttractionLocatorFragment
                        //started when the app is first launched
                        fragmentmanager.popBackStackImmediate(FRAG_ATTRLOC, 0);

                        findViewById(R.id.myMapFragment).setVisibility(View.VISIBLE);

                        transaction.replace(R.id.relative, fragmentmanager.findFragmentByTag(FRAG_ATTRLOC));
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case 1:

                       //Pop up to but not including the attraction locator
                        fragmentmanager.popBackStackImmediate(FRAG_ATTRLOC,0);

                        //Hide mapfragment
                        findViewById(R.id.myMapFragment).setVisibility(View.GONE);

                        fragment = new ItineraryPlannerFragment();
                        transaction.replace(R.id.relative, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case 2:
                        //Pop up to but not including the attraction locator
                        fragmentmanager.popBackStackImmediate(FRAG_ATTRLOC,0);

                        //Hide mapfragment
                        findViewById(R.id.myMapFragment).setVisibility(View.GONE);

                        if(fragmentmanager.findFragmentByTag(FRAG_BUDGET)==null)
                        {
                            fragment = new BudgetManagerFragment();
                            transaction.replace(R.id.relative, fragment,FRAG_BUDGET);
                        }
                        else
                        {
                           transaction.replace(R.id.relative, frag_attrloc);
                        }

                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                                   }
                setTitle(osArray[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    //Save itinerary to file when activity is stopped
    @Override
    protected void onStop()
    {
        ItineraryManager.writeSavedItineraries(this);
        Log.d(TAG, "Stoppping main");
        super.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        //Go to previous fragment on back button
        if(getFragmentManager().getBackStackEntryAt(
                getFragmentManager().getBackStackEntryCount()-1
        ).getName() == FRAG_ATTRLOC)
        {
            Log.d(TAG,"Topmost is frag attrloc, quit app on bacpress");
            super.onBackPressed();
        }
        else
            getFragmentManager().popBackStack();
    }


    @Override
        protected void onSaveInstanceState(Bundle outState) {
    //        super.onSaveInstanceState(outState);
        }


        /**
         * OnClickListner for the search button in attraction locator
         * Handles the search gracefully so that one can search
         * from a custom address string keyed into the edit text
         * or from the address returned by autocompletion
         * updates the textview that displays the destination
         * address and makes the addToItinerary button visible
         */
    public class Search implements View.OnClickListener {

        @Override
        public void onClick(View view)
        {
            //Spell Check
            RobustSpellChecker RSC = new RobustSpellChecker();
            //Get address directly from autocomplete text
            String address="";
            String name =((EditText) findViewById(R.id.autocomplete_places)).getText().toString().trim();
            String autocomplete = AttractionLocatorFragment.mPlaceDetailsText.getText()
                            .toString().split("Address:")[0].trim();

            if(name.equals(autocomplete) && !name.isEmpty())
            {
                address = AttractionLocatorFragment.mPlaceDetailsText.getText()
                        .toString().split("Address:")[1];
            }

            address = RSC.SpellChecker(name);
            String placeName = mapfragment.update(address);
            Log.d(TAG, "address is " + address);

            //Set the placeName text if search address was not taken from autocomplete result
            if(!name.equals(autocomplete))
            {
                AttractionLocatorFragment.mPlaceDetailsText.setVisibility(View.VISIBLE);
                AttractionLocatorFragment.mPlaceButton.setVisibility(View.VISIBLE);
                AttractionLocatorFragment.mPlaceDetailsText.setText("Address: " + placeName);
                AttractionLocatorFragment.mPlaceButton.setOnClickListener(
                        new AttractionLocatorFragment.addtoItinerary(placeName)
                );
            }

            // Check if no view has focus
            // This makes sure that the soft keyboard hides after hitting the search
            view = MainActivity.this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        }

    }
}
