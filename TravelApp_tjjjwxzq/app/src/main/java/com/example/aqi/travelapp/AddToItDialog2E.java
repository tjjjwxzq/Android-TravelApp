package com.example.aqi.travelapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToItDialog2E extends DialogFragment {

   public static AddToItDialog2E newInstance(String[] saveditineraries, CharSequence placename){
        AddToItDialog2E fragment = new AddToItDialog2E();
        Bundle args = new Bundle();
        args.putCharSequenceArray("saved itineraries",saveditineraries );
        args.putCharSequence("placename", placename);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Get arguments
        final String[] saveditineraries = (String[]) getArguments().getCharSequenceArray("saved itineraries");
        final String placename = (String) getArguments().getCharSequence("placename");

        //Create builder with simple list
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose existing itinerary");
        builder.setItems(saveditineraries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItineraryActivity.updateSavedItinerary(saveditineraries[which],
                        placename);
            }
        });
        return builder.create();

    }

}
