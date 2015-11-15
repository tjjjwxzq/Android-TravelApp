package com.example.aqi.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * ImageAdapter for the ItineraryPlannerFragment grid view
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    private LayoutInflater inflater;

    private List<Integer> imageResources;

    private final String TAG = "ImageAdapter";

    public ImageAdapter(Context c, int numres) {
        mContext = c;
        inflater = LayoutInflater.from(mContext);
        imageResources = Arrays.asList(mThumbIds).subList(0,numres);
    }

    public int getCount() {
        return imageResources.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new View for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            view = inflater.inflate(R.layout.it_gridview_cell,null);

        } else {
            view = convertView;
        }

        //Set image
        ImageView image = (ImageView) view.findViewById(R.id.grid_image);
        image.setImageResource(mThumbIds[position]); //assigns image resource to adapter

        //Set text as itinerary name
        TextView text = (TextView) view.findViewById(R.id.grid_text);
        text.setText(ItineraryManager.saveditineraries.get(position).name);

        return view;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.merlion_icon_skyblue, R.drawable.merlion_icon_violet,
            R.drawable.merlion_icon_lilac, R.drawable.merlion_icon_pinklilac,
            R.drawable.merlion_icon_pink, R.drawable.merlion_icon_tangerine,
            R.drawable.merlion_icon_lime, R. drawable.merlion_icon_teal,
            R.drawable.merlion_icon_skyblue, R.drawable.merlion_icon_violet,
            R.drawable.merlion_icon_lilac, R.drawable.merlion_icon_pinklilac,
            R.drawable.merlion_icon_pink, R.drawable.merlion_icon_tangerine,
            R.drawable.merlion_icon_lime, R. drawable.merlion_icon_teal,
            R.drawable.merlion_icon_skyblue, R.drawable.merlion_icon_violet,
            R.drawable.merlion_icon_lilac, R.drawable.merlion_icon_pinklilac,
            R.drawable.merlion_icon_pink, R.drawable.merlion_icon_tangerine,
            R.drawable.merlion_icon_lime, R. drawable.merlion_icon_teal
    };

}
