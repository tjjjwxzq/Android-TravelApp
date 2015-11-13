package com.example.aqi.travelapp;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arbiter on 12/11/2015.
 */
public class ItineraryListAdapter extends BaseAdapter {

    private static final String TAG = "ItListAdapter";

    private int itinerarypos;

    private Context context;

    private SavedItinerary itinerary;

    public ItineraryListAdapter(int itinerarypos, Context context)
    {
        this.itinerarypos = itinerarypos;
        Log.d(TAG, "adapter saveditineraries " + ItineraryManager.saveditineraries);
        Log.d(TAG, "itinerary " + itinerary);
        this.itinerary = ItineraryManager.saveditineraries.get(itinerarypos);
        this.context = context;
    }

    @Override
    public int getCount() {
        return itinerary.destinations.size() + 1;
        //including the final return destination (the hotel)
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.it_list_view, parent, false);
        }


        //Get the destination node name text view
        TextView nodeName = (TextView) view.findViewById(R.id.itinerary_node_name);

        //The last element should be the same as the starting point
        int finposition = position == itinerary.destinations.size()?0:position;

        //If itinerary has been planned, get the transport modes as well
        if(itinerary.destinations.size() == itinerary.itinerary.size() &&
                itinerary.destinations.size() != 1) //new itinerary will always have the destination and itinerary size
        //but an itinerary with just one destination doesn't make sense
        {

            Log.d(TAG, "Itinerary has been planned, fetching list view");
            //The destination node name at this position
            String node1 = itinerary.itinerary.get(finposition);
            //The destination node name at the next position
            String node2 = finposition+1<itinerary.itinerary.size()?
                    itinerary.itinerary.get(finposition+1): itinerary.itinerary.get(0);

            LinearLayout transportll = (LinearLayout) view.findViewById(R.id.itinerary_transport_layout);
            ImageView transportIcon = (ImageView) view.findViewById(R.id.itinerary_transport_icon);
            TextView transportName = (TextView) view.findViewById(R.id.itinerary_transport_text);

            Log.d(TAG, "saved itinerary " + itinerary);

            nodeName.setText(itinerary.itinerary.get(finposition));

            if(position!=itinerary.itinerary.size())
            {
                //Make transport layout visible
                transportll.setVisibility(View.VISIBLE);
                String transportstr;
                switch(itinerary.transportmodes[finposition])
                {
                    case 0:
                        transportIcon.setImageResource(R.drawable.walking_icon);
                        transportstr = context.getResources().getString(R.string.walktext);
                        transportName.setText(String.format(transportstr,
                                CostUtils.getTime(0,node1,node2)));
                        break;
                    case 1:
                        transportIcon.setImageResource(R.drawable.bus_icon);
                        transportstr = context.getResources().getString(R.string.bustext);
                        transportName.setText(String.format(transportstr,
                                CostUtils.getTime(1,node1,node2), CostUtils.getTime(2,node1,node2)));
                        break;
                    case 2:
                        transportIcon.setImageResource(R.drawable.taxi_icon);
                        transportstr = context.getResources().getString(R.string.taxitext);
                        transportName.setText(String.format(transportstr,
                                CostUtils.getTime(2,node1,node2), CostUtils.getCost(2,node1,node2)));
                        break;
                }

            }
       }
        else //otherwise just display the destination names
        {
            Log.d(TAG, "Itinerary not planned, just showing det");
            if(position!=itinerary.destinations.size())
               nodeName.setText(itinerary.destinations.get(finposition));
        }

        return view;
    }
}
