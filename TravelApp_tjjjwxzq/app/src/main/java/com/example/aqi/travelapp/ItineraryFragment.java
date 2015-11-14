package com.example.aqi.travelapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItineraryFragment extends Fragment {

    private static final String TAG = "ItFrag";
    //Fragment initialization parameters
    private static final String ITINERARYPOS = "itposition";

    private TextView itineraryName;

    private ListView mListView;

    private ItineraryListAdapter mAdapter;

    private Button btnplanItinerary;

    private int itinerarypos;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param  itinerarypos  the position of the selected itinerary as stored in
     *                       ItineraryManager.saveditineraries
     * @return A new instance of fragment ItineraryFragment.
     */
    public static ItineraryFragment newInstance(int itinerarypos){
        ItineraryFragment fragment = new ItineraryFragment();
        Bundle args = new Bundle();
        args.putInt(ITINERARYPOS, itinerarypos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get itinerary name
        itinerarypos = getArguments().getInt(ITINERARYPOS);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_itinerary, container, false);

        //Get the text view
        itineraryName = (TextView) root.findViewById(R.id.itinerary_name);
        itineraryName.setText(ItineraryManager.saveditineraries.get(itinerarypos).name);

        //Get the list view
        mListView = (ListView) root.findViewById(R.id.itinerary_list);

        //Iinitalize the ListView adapter
        mAdapter = new ItineraryListAdapter(itinerarypos,getActivity());

        //Set the ListView adapter
        mListView.setAdapter(mAdapter);

        //Get the button view
        btnplanItinerary = (Button) root.findViewById(R.id.btn_plan_itinerary);
        btnplanItinerary.setOnClickListener(new planItineraryListener(itinerarypos));

        return root;
    }

    public class planItineraryListener implements View.OnClickListener
    {
        private int itinerarypos;

        private SavedItinerary itinerary;

        public planItineraryListener(int itinerarypos)
        {
            this.itinerarypos = itinerarypos;
        }

        @Override
        public void onClick(View view)
        {
            itinerary = ItineraryManager.saveditineraries.get(itinerarypos);

            ArrayList<String> plannedit = ItineraryManager.planItinerary(0,
                    ItineraryManager.saveditineraries.get(itinerarypos).destinations);
            if(plannedit != null)
            {
                itinerary.itinerary = plannedit;
                itinerary.transportmodes = CostUtils.getTransportMode(plannedit,100.00)
                .get(0).get(0);
                Log.d(TAG, "saveditineraries" + ItineraryManager.saveditineraries);
                mAdapter.notifyDataSetChanged();
                MainActivity.mapfragment.update(itinerary.itinerary,itinerary.transportmodes);
            }
            else
            {
                Toast.makeText(getActivity(),"Insufficient cost data to plan itinerary. Not" +
                        "all desintation node are supported.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop()
    {
        ItineraryManager.writeSavedItineraries(getActivity());
        Log.d(TAG, "writing saved itineraries");
        super.onStop();
    }
}
