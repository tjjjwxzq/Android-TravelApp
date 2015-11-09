package com.example.aqi.travelapp;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements GoogleApiClient.OnConnectionFailedListener {

    EditText locationtext;
    ListView locationlist;
    ArrayAdapter<String> locarrayadapter;
    ArrayList<String> locationsarr;

    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView mAutocompleteView;

    private TextView mPlaceDetailsText;

    private Button mPlaceButton;

    private static final LatLngBounds SINGAPORE = new LatLngBounds(
            new LatLng(1.251484, 103.618240), new LatLng(1.464026, 104.110222));

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        // Construct a GoogleApiClient for the {@link Places#GEO_DATA_API} using AutoManage
        // functionality, which automatically sets up the API client to handle Activity lifecycle
        // events. If your activity does not extend FragmentActivity, make sure to call connect()
        // and disconnect() explicitly.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        //Retrieve the AutoCompleteTextView that will display Place suggestions
        mAutocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete_places);

        //Register a listener that receives callbacks when a suggestion is selected
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        //Retrieve the TextView that will display the details of the selected place
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);

        //Set up the adapter that will retrieve suggestions from the Places Geo Data API
        //that cover the entire world (no filter)
        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, SINGAPORE, null);
        mAutocompleteView.setAdapter(mAdapter);

        //Retrieve the text
        locationsarr = new ArrayList<>();
        locationlist = (ListView) findViewById(R.id.main_list);
        locarrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationsarr);
        locationlist.setAdapter(locarrayadapter);
    }

    /**
     * Listener that handles selections from suggestions from the AutocompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            /*
            Retrieve the place ID the selected item from the Adapter
            Each Place suggestion is stored as an AutocompletePrediction
            in the Adapter
             */

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
            Issue a request to the Places Geo Data API to retrieve a Place object with
            additional details about the place
             */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }

    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place
     * result in the details view on screen (maybe change it top three results?)
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if(!places.getStatus().isSuccess())
            {
                //Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }

            //Get the Place object from the buffer
            final Place place = places.get(0);

            //Format details of the place for display and show it in a TextView
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getAddress()));

            //Create new button for adding place to itinerary
            mPlaceButton.setText(R.string.addtoit);
            LinearLayout ll = (LinearLayout) findViewById(R.id.main_linearlayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(mPlaceButton, lp);

            Log.i(TAG, "Place details received: " + place.getName());

            places.release(); //release the buffer
        }
    };

    /**
     * Returns the place details as a styled HTML text
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, CharSequence address)
    {
        Log.e(TAG, res.getString(R.string.place_details, name, address));
        return Html.fromHtml(res.getString(R.string.place_details, name, address));
    }

    /**
     * Called when the Acitivty could not connect to GooglePlay services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be ispected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


    public void findLocation(View view)
    {
        locationsarr.add(locationtext.getText().toString());
        locarrayadapter.notifyDataSetChanged();
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

        return super.onOptionsItemSelected(item);
    }
}
