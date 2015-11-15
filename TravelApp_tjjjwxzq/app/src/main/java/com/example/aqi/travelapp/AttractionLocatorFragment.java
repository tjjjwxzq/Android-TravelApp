package com.example.aqi.travelapp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class AttractionLocatorFragment extends Fragment
 implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "AttrLocFrag";

    private View root;

    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mPlaceAdapter;

    private AutoCompleteTextView mAutocompleteView;

    public static TextView mPlaceDetailsText;

    public static ImageButton mPlaceButton;

    private static FragmentManager fragmentManager;

    private ImageButton mFindButton;

    private ImageButton mClearButton;

    private static final LatLngBounds SINGAPORE = new LatLngBounds(
            new LatLng(1.251484, 103.618240), new LatLng(1.464026, 104.110222));

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Construct a GoogleApiClient for the {@link Places#GEO_DATA_API}
        // Not using AutoManager functionality, which automatically sets up the API client to handle Activity lifecycle
        // events, because the fragment can't inherit from FragmentAcitvity
        // connect() and disconnect() are called explicitly
        // in onStart() and onStop()
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();

        fragmentManager = getFragmentManager();


    }

    @Override
    public void onStart(){
        super.onStart();
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();

        //Set map to visible
        getActivity().findViewById(R.id.myMapFragment).setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop(){
        if(mGoogleApiClient!=null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();

        super.onStop();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.fragment_attraction_locator, container, false);


        //Retrieve the AutoCompleteTextView that will display Place suggestions
        mAutocompleteView = (AutoCompleteTextView) root.findViewById(R.id.autocomplete_places);

        //Register a listener that receives callbacks when a suggestion is selected
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        //Retrieve the TextView that will display the details of the selected place
        mPlaceDetailsText = (TextView) root.findViewById(R.id.place_details);

        //Retrieve the Add to Itinerary button
        mPlaceButton = (ImageButton) root.findViewById(R.id.btn_addtoit);

        //Set up the adapter that will retrieve suggestions from the Places Geo Data API
        //that cover the entire world (no filter)
        mPlaceAdapter = new PlaceAutocompleteAdapter(this.getActivity(), mGoogleApiClient, SINGAPORE, null);
        mAutocompleteView.setAdapter(mPlaceAdapter);

        //Retrieve the Find button
        mFindButton = (ImageButton) root.findViewById(R.id.btn_findloc);
        mFindButton.setOnClickListener(MainActivity.searchlistner);

        //Retrieve the Clear edittext button
        mClearButton = (ImageButton) root.findViewById(R.id.btn_clear_edittext);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mAutocompleteView.setText("");
            }
        });

        return root;
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

            final AutocompletePrediction item = mPlaceAdapter.getItem(position);
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

            mAutocompleteView.setText(primaryText);

            Toast.makeText(getActivity().getApplicationContext(), "Clicked: " + primaryText, Toast.LENGTH_SHORT).show();
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
            mPlaceDetailsText.setVisibility(View.VISIBLE);

            //Make add to itinerary button visible and pass the place name to its onClickListener
            mPlaceButton.setOnClickListener(new addtoItinerary(place.getName()));
            mPlaceButton.setVisibility(View.VISIBLE);

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

        Toast.makeText(getActivity(),
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Listener creates a dialogfragment which allows user to choose either
     * to save to an existing itinerary or a new itinerary
     */
    public static class addtoItinerary implements View.OnClickListener {

        //Pass the placename to the dialog
        private CharSequence placename;

        public addtoItinerary(CharSequence placename)
        {
            this.placename = placename;
        }

        @Override
        public void onClick(View view)
        {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            DialogFragment newFragment = AddToItDialog1.newInstance(placename);
            newFragment.show(ft,"dialog");
        }

    }




}
