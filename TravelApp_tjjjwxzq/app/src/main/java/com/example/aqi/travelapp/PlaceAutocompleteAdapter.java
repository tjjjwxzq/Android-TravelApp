package com.example.aqi.travelapp;

/**
 * Created by aqi on 3/11/15.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Adapter that handles Autocomplete requests from the Places Geo Data API.
 * {@link AutocompletePrediction} results from the API are frozen and stored directly in this
 * adapter. (See {@link AutocompletePrediction#freeze()}.)
 * <p>
 * Note that this adapter requires a valid {@link com.google.android.gms.common.api.GoogleApiClient}.
 * The API client must be maintained in the encapsulating Activity, including all lifecycle and
 * connection states. The API client must be connected with the {@link Places#GEO_DATA_API} API.
 */
public class PlaceAutocompleteAdapter
    extends ArrayAdapter<AutocompletePrediction> implements Filterable {

    private static final String TAG = "PlaceAutocompAdapter";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    /**
     * Current results returned by this addapter
     */
    private ArrayList<AutocompletePrediction> mResultList;

    /**
     * Handles autocomplete requests
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * The bounds used Places Geo Data autocomplete API requests
     */
    private LatLngBounds mBounds;

    /**
     * The autocomplete filter used to restrict queries to a specific set of place types
     */
    private AutocompleteFilter mPlaceFilter;

    /**
     * Initializes with a resource for text ows and autocomplete query bounds
     * @see android.widget.ArrayAdapter#ArrayAdapter(android.content.Context, int)
     */
    public PlaceAutocompleteAdapter(Context context, GoogleApiClient googleApiClient,
                                    LatLngBounds bounds, AutocompleteFilter filter)
    {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        mGoogleApiClient = googleApiClient;
        mBounds = bounds;
        mPlaceFilter = filter;
    }

    /**
     * Sets the bounds for all subsequent queries
     */
    public void setBounds(LatLngBounds bounds)
    {
        mBounds = bounds;
    }

    /**
     * Returns the number of results received in the last autocomplete query
     */
    @Override
    public int getCount(){
        return mResultList.size();
    }

    /**
     * Returns an item from the last autocomplete query
     */
    @Override
    public AutocompletePrediction getItem(int position)
    {
        return mResultList.size()!=0?mResultList.get(position):null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = super.getView(position, convertView, parent);

        //Sets the primary and secondary text for a row
        //Note that getPrimaryText() and getSecondaryText() return a CharSequence that may contain
        // styling based on the given CharacterStyle

        AutocompletePrediction item = getItem(position);

        if(item!=null)
        {
            TextView textView1 = (TextView) row.findViewById(android.R.id.text1);
            TextView textView2 = (TextView) row.findViewById(android.R.id.text2);
            textView1.setText(item.getPrimaryText(STYLE_BOLD));
            textView2.setText(item.getSecondaryText(STYLE_BOLD));
        }


        return row;
    }

    /**
     * Retursn the filter for the current set of autocomplete results
     */
    @Override
    public Filter getFilter(){
        return new Filter(){
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint){
                FilterResults results = new FilterResults();
                //Skip the autocomplete query if no constraints are given
                if(constraint != null){
                    ///Query the autocomplete API for the (constraint) search string
                    mResultList = getAutocomplete(constraint);
                    if(mResultList!=null)
                    {
                        //The API successfully returned results
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results){
                if(results != null && results.count >0)
                {
                    //add filtering to results list here based on whether
                    //the autocomplete prediction full text contains "Singapore"
                    //filter in publishResults which runs on the UI thread, and
                    //not in the background thread
                    mResultList = new ArrayList<AutocompletePrediction>(
                            Collections2.filter(mResultList, new Predicate<AutocompletePrediction>() {
                                @Override
                                public boolean apply(AutocompletePrediction input) {
                                    return input.getFullText(null).toString().contains("Singapore");
                                }
                            })
                    );
                    //The API returned at least one result, update the data
                    notifyDataSetChanged();
                }
                else
                {
                    //The API did not return any results, invalidate the data set
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue)
            {
                //Override this method to display a readable result in the AutocompleteTextView
                //when clicked
                if (resultValue instanceof AutocompletePrediction)
                {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                }else
                {
                    return super.convertResultToString(resultValue);
                }
            }

        };

    }

    /**
     * Submits an autocomplete query to the Places Geo Data Autocomplete API.
     * Results are returned as frozen AutocompletePrediction objects, ready to be cached.
     * These are objects to store the Place ID and description that the API returns
     * Returns an empty list if no results were found
     * Reutrns null if the API client is not available or the query did not complete
     * successfully.
     * This method MUST be called off the main UI thread, as it will block until data is
     * returned from the API, which may include a network request.
     *
     * @param constraint Autcocomplete query string
     * @return Results from the autocomplete API or null if the query was not successful
     * @see Places#GEO_DATA_API#getAutocomplete(CharSequence)
     * @see AutocompletePrediction#freeze()
     */
    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint){
        if(mGoogleApiClient.isConnected()){
            Log.i(TAG, "Starting autocomplete query for: " + constraint);

            //Submit the query to the autocomplete API and retrieve a PnedingResult that
            //will contain the results when the query completes
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                            mBounds, mPlaceFilter);

            //This method should have been called off the main UI thread. Block and wait for
            //at most 60s for a result from the API
            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS);

            // Confirm that the query completed successfully, otherwise return null
            final Status status = autocompletePredictions.getStatus();
            if(!status.isSuccess())
            {
                Toast.makeText(getContext(), "Error contacting API. Please ensure that" +
                                " you are connected to the Internet",
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
            + " predictions.");

            // Freeze the results immutable representation (as an arraylist of the objects
            // contained in the buffer) that can be stored safely
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        }

        Log.e(TAG, "Google API client is not connected for autocomplete query.");
        return null;
    }



}
