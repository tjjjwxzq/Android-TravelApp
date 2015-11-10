package com.example.aqi.travelapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
 * Activities that contain this fragment must implement the
 * {@link AttractionLocatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttractionLocatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttractionLocatorFragment extends Fragment
 implements GoogleApiClient.OnConnectionFailedListener {

    private View root;

    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mPlaceAdapter;

    private AutoCompleteTextView mAutocompleteView;

    private TextView mPlaceDetailsText;

    private Button mPlaceButton;

    private static final LatLngBounds SINGAPORE = new LatLngBounds(
            new LatLng(1.251484, 103.618240), new LatLng(1.464026, 104.110222));

    private static final String TAG = "AttrLocFrag";

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
    }

    @Override
    public void onStart(){
        super.onStart();
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop(){
        if(mGoogleApiClient!=null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    public static Fragment newInstance(Context context) {
        AttractionLocatorFragment frag = new AttractionLocatorFragment();
        return frag;
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

        //Initialize the add itinerary button
        mPlaceButton = new Button(this.getActivity());

        //Set up the adapter that will retrieve suggestions from the Places Geo Data API
        //that cover the entire world (no filter)
        mPlaceAdapter = new PlaceAutocompleteAdapter(this.getActivity(), mGoogleApiClient, SINGAPORE, null);
        mAutocompleteView.setAdapter(mPlaceAdapter);


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

            //Create new button for adding place to itinerary
            mPlaceButton.setText(R.string.addtoit);
            mPlaceButton.setOnClickListener(new addtoItinerary());
            LinearLayout ll = (LinearLayout) root.findViewById(R.id.loc_linearlayout);
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

        Toast.makeText(getActivity(),
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    public class addtoItinerary implements View.OnClickListener(View view) {

        public addtoItinerary(){}

        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_addtoit, null);

            builder.setView(dialogView);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addtobudget, null);
        final EditText meow = (EditText) dialogView.findViewById(R.id.additionalBudget);
        builder.setView(dialogView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //user clicked ok button

                addedBudget = Double.parseDouble((meow).getText().toString());
                budget += Math.round(addedBudget * 100) / 100;
                updateBudgetText();
                updateRemainingText();
                Toast.makeText(MainActivity.this, "Budget updated successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
